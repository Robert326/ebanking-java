package org.poo.cb;

public interface Command {
    void execute();

}

class CreateUser implements Command {
    private String firstName;
    private String name;
    private String email;
    private String address;

    private boolean isPremium;

    public CreateUser(String firstName, String name, String email, String address, boolean isPremium) {
        this.firstName = firstName;
        this.name = name;
        this.email = email;
        this.address = address;
        this.isPremium = isPremium;
    }

    public void execute() {
        User newUser = new User.Builder(email)
                .firstName(firstName)
                .lastname(name)
                .address(address)
                .isPremium(isPremium)
                .build();
        App.getInstance().addUser(newUser);
    }
}

class AddFriend implements Command {
    private String emailUser;
    private String emailFriend;

    public AddFriend(String emailUser, String emailFriend) {
        this.emailUser = emailUser;
        this.emailFriend = emailFriend;
    }

    @Override
    public void execute() {
        App.getInstance().addFriend(emailUser, emailFriend);
    }
}

class ListUser implements Command {
    private String emailUser;

    public ListUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public void execute() {
        App.getInstance().listUser(emailUser);
    }
}

class AccountCommand implements Command {
    private String userEmail;
    private String accountType;
    private double initialAmount;

    public AccountCommand(String userEmail, String accountType, double initialAmount) {
        this.userEmail = userEmail;
        this.accountType = accountType;
        this.initialAmount = initialAmount;
    }

    @Override
    public void execute() {
        App.getInstance().addAccount(userEmail, AccountType.valueOf(accountType), initialAmount);
    }
}

class AddMoneyCommand implements Command {
    private String userEmail;
    private String accountType;
    private double amount;

    public AddMoneyCommand(String userEmail, String accountType, double amount) {
        this.userEmail = userEmail;
        this.accountType = accountType;
        this.amount = amount;
    }

    public void execute() {
        App.getInstance().addMoney(userEmail, AccountType.valueOf(accountType), amount);
    }

}

class ExchangeMoneyCommand implements Command {
    private String userEmail;
    private String accountType;

    private String wantedAccountType;
    private double amount;

    public ExchangeMoneyCommand(String userEmail, String accountType, String wantedAccountType, double amount) {
        this.userEmail = userEmail;
        this.accountType = accountType;
        this.wantedAccountType = wantedAccountType;
        this.amount = amount;
    }

    public void execute() {
        App.getInstance().ExchangeMoney(userEmail, AccountType.valueOf(accountType),
                AccountType.valueOf(wantedAccountType), amount);
    }
}

class TransferMoneyCommand implements Command {
    private String userEmail;
    private String friendEmail;
    private String accountType;
    private double amount;

    public TransferMoneyCommand(String userEmail, String friendEmail, String accountType, double amount) {
        this.accountType = accountType;
        this.userEmail = userEmail;
        this.amount = amount;
        this.friendEmail = friendEmail;
    }

    public void execute() {
        App.getInstance().TransferMoney(userEmail, friendEmail, AccountType.valueOf(accountType), amount);
    }
}

class ShowUserPortfolioCommand implements Command {
    private String userEmail;

    public ShowUserPortfolioCommand(String userEmail) {
        this.userEmail = userEmail;
    }

    public void execute() {
        App.getInstance().ShowPortfolio(userEmail);
    }
}

class BuyStockCommand implements Command {
    private String stockType;
    private int amount;
    private String userEmail;

    private String filepathStocks;

    public BuyStockCommand(String userEmail, String stockType, int amount, String filepathStocks) {
        this.stockType = stockType;
        this.amount = amount;
        this.filepathStocks = filepathStocks;
        this.userEmail = userEmail;
    }

    public void execute() {
        App.getInstance().BuyStocks(userEmail, stockType, amount, filepathStocks);
    }
}

class RecommendStockCommand implements Command {
    private String filepath;

    public RecommendStockCommand(String filepath) {
        this.filepath = filepath;
    }

    public void execute() {
        App.getInstance().RecommendingStocks(filepath);
    }
}

class BuyPremiumCommand implements Command {
    private String userEmail;

    public BuyPremiumCommand(String userEmail) {
        this.userEmail = userEmail;
    }

    public void execute() {
        App.getInstance().BuyPremium(userEmail);
    }

}



