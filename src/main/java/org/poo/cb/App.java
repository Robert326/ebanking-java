package org.poo.cb;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    private static App instance;
    private Map<String, User> users;
    private String filepathCommands;

    private ArrayList<String> stocksToBuy;
    private String filepathStocks;
    private String filepathExchangeRates;
    private List<String> fileContains;

    private App() {
        users = new HashMap<>();
    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public String getFilepathStocks() {
        return filepathStocks;
    }

    public void setFilepathStocks(String filepathStocks) {
        this.filepathStocks = filepathStocks;
    }

    public void addRecommended(String name) {
        this.stocksToBuy.add(name);
    }

    public void setFilepathExchangeRates(String filepathExchangeRates) {
        this.filepathExchangeRates = filepathExchangeRates;
    }

    public void setFilepathCommands(String filepathCommands) {
        this.filepathCommands = filepathCommands;
    }

    public void afisUseri() {
        for (User user : users.values()) {
            System.out.println("User Name: " + user.getName());
        }
    }

    public void getFileContains(String filepathCommands) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filepathCommands))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.fileContains = lines;
    }

    public List<String> getFile() {
        return fileContains;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void addUser(User user) {

        if (!users.containsKey(user.getEmail())) {
            users.put(user.getEmail(), user);
        } else {
            System.out.println("User with " + user.getEmail() + " already exists");
        }
    }

    public User getUser(String email) {
        return users.get(email);
    }

    public void addFriend(String userEmail, String friendEmail) {
        User user = getUser(userEmail);
        User friend = getUser(friendEmail);

        if (user == null) {
            System.out.println("User with " + userEmail + " doesn't exist");
            return;
        }

        if (friend == null) {
            System.out.println("User with " + friendEmail + " doesn't exist");
            return;
        }
        ArrayList<String> Userfriends = user.getFriends();
        ArrayList<String> FriendFriends = friend.getFriends();
        //search for the friend's mail in friend's list
        if (Userfriends.contains(friendEmail)) {
            System.out.println("User with " + friendEmail + " is already a friend");
            return;
        }

        //if it doesn't exist we add it to each-other
        Userfriends.add(friendEmail);
        user.setFriends(Userfriends);
        FriendFriends.add(userEmail);
        friend.setFriends(FriendFriends);

    }

    public void listUser(String email) {
        User user = users.get(email);
        if (user == null) {
            System.out.println("User with " + email + " doesn't exist");
        }
        System.out.println(GsonTransformer.convertToJson(user));
    }

    public void addAccount(String userMail, AccountType account, double initialAmount) {
        //first check the existence of the account in users accounts
        User user = getUser(userMail);
        ArrayList<Account> accounts = user.getAccounts();
        for (Account userAcc : accounts) {
            if (userAcc.getType().equals(account.name())) {
                System.out.println("Account in currency " + account + " already exists for user");
                return;
            }
        }
        AccountFactory accountFactory = AccountFactory.getInstance();
        Account newAccount = accountFactory.createAccount(account, 0);
        accounts.add(newAccount);
        user.setAccounts(accounts);
    }

    public void addMoney(String userMail, AccountType type, double amount) {
        User user = getUser(userMail);
        ArrayList<Account> accounts = user.getAccounts();
        for (Account userAcc : accounts) {
            if (userAcc.getType().equals(type.name())) {
                userAcc.addMoney(amount);
                return;
            }
        }
        user.setAccounts(accounts);
    }

    public double Exchange(String filepath, AccountType sourceAccount, AccountType destinationAccount) {
        double exchangeRate = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            int value = 0;
            line = reader.readLine();
            String[] words = line.split(",");
            for (int i = 0; i < words.length; i++) {
                if (words[i].equals(sourceAccount.name())) {
                    value = i;
                }
            }
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(destinationAccount.name())) {
                    exchangeRate = Double.parseDouble(values[value]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (exchangeRate == 0) {
            System.out.println("problem");
        }
        return exchangeRate;

    }

    public void ExchangeMoney(String userEmail, AccountType sourceAccount, AccountType destinationAccount, double amount) {
        //check to see if enough money exists in the source account
        User user = getUser(userEmail);
        double exchangeRate = Exchange(filepathExchangeRates, sourceAccount, destinationAccount);

        ArrayList<Account> accounts = user.getAccounts();
        for (Account userAcc : accounts) {
            if (userAcc.getType().equals(sourceAccount.name())) {
                if (userAcc.getAmount() < amount) {
                    System.out.println("Insufficient amount in account " + sourceAccount + " for exchange");
                    return;
                }
                if ((amount * exchangeRate > 0.5 * userAcc.getAmount()) && !user.isPremium()) {
                    //the commission of 1% applies
                    userAcc.withdraw(amount * exchangeRate + amount * exchangeRate / 100);
                } else if (amount * exchangeRate <= 0.5 * userAcc.getAmount() || user.isPremium()) {
                    userAcc.withdraw(amount * exchangeRate);
                }
                //add the money to destination
                for (Account destAcc : accounts) {
                    if (destAcc.getType().equals(destinationAccount.name())) {
                        destAcc.addMoney(amount);
                    }
                }
            }
        }
        user.setAccounts(accounts);
    }

    public void TransferMoney(String userEmail, String friendEmail, AccountType sourceAccount, double amount) {
        User user = getUser(userEmail);
        User friend = getUser(friendEmail);
        boolean everythingFine = false;
        ArrayList<Account> accounts = user.getAccounts();
        ArrayList<String> usersFriends = user.getFriends();
        for (String email : usersFriends) {
            if (email.equals(friendEmail)) {
                everythingFine = true;
                break;
            }
        }
        if (!everythingFine) {
            System.out.println("You are not allowed to transfer money to " + friendEmail);
        }
        ArrayList<Account> friendAccounts = friend.getAccounts();
        for (Account account : accounts) {
            if (account.getType().equals(sourceAccount.name())) {
                if (account.getAmount() <= amount) {
                    System.out.println("Insufficient amount in account " + account.getType() + " for transfer");
                    return;
                }
                for (Account friendAcc : friendAccounts) {
                    if (friendAcc.getType().equals(sourceAccount.name())) {
                        friendAcc.addMoney(amount);
                        account.withdraw(amount);
                    }
                }
            }
        }
    }

    public void ShowPortfolio(String userEmail) {
        User user = getUser(userEmail);
        if (user == null) {
            System.out.println("User with " + userEmail + " doesn't exist");
        }
        JsonObject userJson = new JsonObject();
        JsonArray stocksArray = new Gson().toJsonTree(user.getStocks()).getAsJsonArray();
        userJson.add("stocks", stocksArray);

        JsonArray accountsArray = new JsonArray();
        for (Account account : user.getAccounts()) {
            JsonObject accountJson = new JsonObject();
            accountJson.addProperty("currencyname", account.getType());
            accountJson.addProperty("amount", String.format("%.2f", account.getAmount()));
            accountsArray.add(accountJson);
        }
        userJson.add("accounts", accountsArray);

        String userJsonString = new Gson().toJson(userJson);
        System.out.println(userJsonString);
    }

    public void BuyStocks(String userEmail, String stockType, int numberOfStocks, String filepathStocks) {
        double stockvalue = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepathStocks));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(stockType)) {
                    stockvalue = Double.parseDouble(values[values.length - 1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = getUser(userEmail);
        ArrayList<Account> accounts = user.getAccounts();
        for (Account account : accounts) {
            if (account.getType().equals("USD")) {
                if (account.getAmount() < stockvalue * numberOfStocks) {
                    System.out.println("Insufficient amount in account for buying stock");
                    return;
                }
                //in case you have enough money you will buy them
                account.withdraw(numberOfStocks * stockvalue);
                ArrayList<Stocks> stocks = user.getStocks();
                Stocks stock = new Stocks(stockType, numberOfStocks);
                stocks.add(stock);
                user.setStocks(stocks);
            }
        }
    }

    public void setStocksToBuy(ArrayList<String> stocksToBuy) {
        this.stocksToBuy = stocksToBuy;
    }

    public void RecommendingStocks(String filepathStocks) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepathStocks));
            String line;
            line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                double lastFiveDays = 0;
                double lastTenDays = 0;
                for (int i = 1; i < values.length; i++) {
                    if (i < 6) {
                        lastTenDays += Double.parseDouble(values[i]);
                    } else {
                        lastTenDays += Double.parseDouble(values[i]);
                        lastFiveDays += Double.parseDouble(values[i]);
                    }
                }
                double lastFiveDaysAverage = lastFiveDays / 5.0;
                double lastTenDaysAverage = lastTenDays / 10.0;
                if (lastFiveDaysAverage > lastTenDaysAverage) {
                    if (this.stocksToBuy != null) {
                        this.stocksToBuy.add(values[0]);
                    } else {
                        this.stocksToBuy = new ArrayList<>();
                        this.stocksToBuy.add(values[0]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("stockstobuy", gson.toJsonTree(this.stocksToBuy));
        String json = gson.toJson(jsonObject);
        System.out.println(json);

    }

    public void BuyPremium(String userEmail) {
        User user = users.get(userEmail);
        if (user == null) {
            System.out.println("User with " + userEmail + " doesn't exist");
            return;
        }

        ArrayList<Account> accounts = user.getAccounts();
        for (Account account : accounts) {
            if (account.getType().equals("USD")) {
                if (account.getAmount() < 100) {
                    System.out.println("Insufficient amount in account for buying premium option");
                    return;
                }
                //in case there's money to buy the premium account the user buys it
                account.withdraw(100);
                user.setPremium(true);
            }
        }
    }

    public void clear() {
        users.clear();
        if (stocksToBuy != null)
            stocksToBuy.clear();
    }

    public void executeCommand(Command command) {
        command.execute();
    }

}
