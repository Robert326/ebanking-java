package org.poo.cb;

import java.util.ArrayList;

public class User {
    private String email;
    private String firstName;
    private String lastname;

    private String address;
    transient private ArrayList<Stocks> stocks;
    transient private ArrayList<Account> accounts;
    private ArrayList friends;

    public User(String firstName, String name, String email, String address) {
        this.firstName = firstName;
        this.lastname = name;
        this.email = email;
        this.address = address;
        this.friends = new ArrayList();
        this.accounts = new ArrayList<>();
        this.stocks = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
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
}
