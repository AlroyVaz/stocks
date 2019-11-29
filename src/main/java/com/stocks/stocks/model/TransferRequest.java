package com.stocks.stocks.model;

public class TransferRequest {
    private String bankId;
    private double amount;

    public TransferRequest(String bankId, double amount) {
        this.bankId = bankId;
        this.amount = amount;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
