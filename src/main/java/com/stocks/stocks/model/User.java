package com.stocks.stocks.model;

import java.util.List;

public class User {
    private Credentials credentials;
    private String physicalAddress;
    private String emailAddress;
    private String forgotPassword;
    private double balance;
    private List<BankAccount> bankAccountList;
    private List<Stock> stockList;

    public User(Credentials credentials, String physicalAddress, String emailAddress, String forgotPassword, double balance, List<BankAccount> bankAccountList, List<Stock> stockList) {
        this.credentials = credentials;
        this.physicalAddress = physicalAddress;
        this.emailAddress = emailAddress;
        this.forgotPassword = forgotPassword;
        this.balance = balance;
        this.bankAccountList = bankAccountList;
        this.stockList = stockList;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getForgotPassword() {
        return forgotPassword;
    }

    public void setForgotPassword(String forgotPassword) {
        this.forgotPassword = forgotPassword;
    }

    public List<BankAccount> getBankAccountList() {
        return bankAccountList;
    }

    public void setBankAccountList(List<BankAccount> bankAccountList) {
        this.bankAccountList = bankAccountList;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }
}
