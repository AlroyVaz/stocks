package com.stocks.stocks.model;

import java.util.List;

public class BankAccountWrapper {
    private List<BankAccount> bankAccountList;

    public BankAccountWrapper(List<BankAccount> bankAccountList) {
        this.bankAccountList = bankAccountList;
    }

    public List<BankAccount> getBankAccountList() {
        return bankAccountList;
    }

    public void setBankAccountList(List<BankAccount> bankAccountList) {
        this.bankAccountList = bankAccountList;
    }
}
