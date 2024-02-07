package org.poo.cb;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args == null) {
            System.out.println("Running Main");
        } else {

            String filepathCommands = "src/main/resources/" + args[2];
            String filepathStockValues = "src/main/resources/" + args[1];
            String filepathExchangeRates = "src/main/resources/" + args[0];
            App app = App.getInstance();
            app.clear();
            app.setFilepathCommands(filepathCommands);
            app.getFileContains(filepathCommands);
            app.setFilepathExchangeRates(filepathExchangeRates);
            List<String> contents = app.getFile();
            for (String line : contents) {
                String[] words = line.split(" ");
                if (words.length >= 2) {
                    String command = words[0] + " " + words[1];
                    switch (command) {
                        case ("CREATE USER"):
                            CreateUser createUserCommand = new CreateUser(words[3], words[4], words[2],
                                    String.join(" ", Arrays.copyOfRange(words, 5, words.length)));
                            app.executeCommand(createUserCommand);
                            break;
                        case ("ADD FRIEND"):
                            AddFriend addFriendCommand = new AddFriend(words[2], words[3]);
                            app.executeCommand(addFriendCommand);
                            break;
                        case ("LIST USER"):
                            ListUser listUserCommand = new ListUser(words[2]);
                            app.executeCommand(listUserCommand);
                            break;
                        case ("ADD ACCOUNT"):
                            AccountCommand addAccountCommand = new AccountCommand(words[2], words[3], 0);
                            app.executeCommand(addAccountCommand);
                            break;
                        case ("ADD MONEY"):
                            AddMoneyCommand addMoneyCommand = new AddMoneyCommand(words[2], words[3],
                                    Double.parseDouble(words[4]));
                            app.executeCommand(addMoneyCommand);
                            break;
                        case ("EXCHANGE MONEY"):
                            ExchangeMoneyCommand exchangeMoneyCommand = new ExchangeMoneyCommand(words[2],
                                    words[3], words[4], Double.parseDouble(words[5]));
                            app.executeCommand(exchangeMoneyCommand);
                            break;
                        case ("TRANSFER MONEY"):
                            TransferMoneyCommand transferMoneyCommand = new TransferMoneyCommand(words[2],
                                    words[3], words[4], Double.parseDouble(words[5]));
                            app.executeCommand(transferMoneyCommand);
                            break;
                        case ("BUY STOCKS"):

                            break;
                        case ("RECOMMEND STOCKS"):
                            break;
                        case ("LIST PORTFOLIO"):
                            ShowUserPortfolioCommand showUserPortfolioCommand =
                                    new ShowUserPortfolioCommand(words[2]);
                            app.executeCommand(showUserPortfolioCommand);
                            break;
                    }
                }
            }
        }

    }
}