package org.poo.cb;

import java.util.ArrayList;

public class User {
    private String email;
    private String firstName;
    private String lastname;

    transient private boolean isPremium;
    private String address;
    transient private ArrayList<Stocks> stocks;
    transient private ArrayList<Account> accounts;
    private ArrayList friends;

    private User(Builder builder) {
        this.firstName = builder.firstName;
        this.lastname = builder.lastname;
        this.email = builder.email;
        this.isPremium = builder.isPremium;
        this.address = builder.address;
        this.stocks = builder.stocks;
        this.accounts = builder.accounts;
        this.friends = builder.friends;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean isPremium() {
        return isPremium;
    }
    public void setStocks(ArrayList<Stocks> stocks) {
        this.stocks = stocks;
    }

    public String getName() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<Stocks> getStocks() {
        return stocks;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public ArrayList getFriends() {
        return friends;
    }

    public void setFriends(ArrayList friends) {
        this.friends = friends;
    }
    public static class Builder {
        private String email;
        private String firstName;
        private String lastname;
        private boolean isPremium = false; // Implicit, un utilizator nu este premium
        private String address;
        private ArrayList<Stocks> stocks = new ArrayList<>();
        private ArrayList<Account> accounts = new ArrayList<>();
        private ArrayList<String> friends = new ArrayList<>();

        public Builder(String email) {
            this.email = email;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder isPremium(boolean isPremium) {
            this.isPremium = isPremium;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder stocks(ArrayList<Stocks> stocks) {
            this.stocks = stocks;
            return this;
        }

        public Builder accounts(ArrayList<Account> accounts) {
            this.accounts = accounts;
            return this;
        }

        public Builder friends(ArrayList<String> friends) {
            this.friends = new ArrayList<>(friends);
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }
}
