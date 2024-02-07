package org.poo.cb;

abstract class Account {

    protected double amount;
    private String currencyName;


    protected Account(String currencyName, double amount) {
        this.amount = amount;
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void addMoney(double amount) {
        this.amount += amount;
    }

    public void withdraw(double amount) {
        this.amount -= amount;

    }

    public abstract String getType();

}

class EURAccount extends Account {
    public EURAccount(double amount) {
        super("EUR", amount);
    }

    public String getType() {
        return "EUR";
    }
}

class USDAccount extends Account {
    public USDAccount(double amount) {
        super("USD", amount);
    }

    public String getType() {
        return "USD";
    }
}

class CADAccount extends Account {
    public CADAccount(double amount) {
        super("CAD", amount);
    }

    public String getType() {
        return "CAD";
    }
}

class GBPAccount extends Account {
    public GBPAccount(double amount) {
        super("GBP", amount);
    }

    public String getType() {
        return "GBP";
    }
}

class JPYAccount extends Account {
    public JPYAccount(double amount) {
        super("JPY", amount);
    }

    public String getType() {
        return "JPY";
    }
}



