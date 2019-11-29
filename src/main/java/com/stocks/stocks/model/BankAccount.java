package com.stocks.stocks.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "BankAccountCollection")
public class BankAccount {
    @Id
    private ObjectId id;
    private ObjectId userId;
    private String bankRoutingNumber;
    private String bankAccountNumber;
    private double money;

    public BankAccount(String bankRoutingNumber, String bankAccountNumber) {
        this.id = new ObjectId();
        this.userId = null;
        this.bankRoutingNumber = bankRoutingNumber;
        this.bankAccountNumber = bankAccountNumber;
        this.money = 1000;
    }

    public ObjectId getId() { return id; }

    public ObjectId getUserId() { return userId; }

    public void setUserId(ObjectId userId) { this.userId = userId; }

    public String getBankRoutingNumber() {
        return bankRoutingNumber;
    }

    public void setBankRoutingNumber(String bankRoutingNumber) {
        this.bankRoutingNumber = bankRoutingNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
