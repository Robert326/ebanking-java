package org.poo.cb;

public class AccountFactory {
    private static AccountFactory instance;

    private AccountFactory() {
    }

    public static AccountFactory getInstance() {
        if (instance == null) {
            instance = new AccountFactory();
        }
        return instance;
    }

    public Account createAccount(AccountType accountType, double amount) {
        return switch (accountType) {
            case USD -> new USDAccount(amount);
            case EUR -> new EURAccount(amount);
            case CAD -> new CADAccount(amount);
            case GBP -> new GBPAccount(amount);
            case JPY -> new JPYAccount(amount);
            default -> throw new IllegalArgumentException();
        };
    }
}
