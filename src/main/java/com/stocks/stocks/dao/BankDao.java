package com.stocks.stocks.dao;

import com.stocks.stocks.model.BankAccount;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bankDao")
public class BankDao {
    public void addBank(String username, List<BankAccount> bankAccountList) {
        // find user in DB from username
        // add bank to user in DB
    }

    public boolean transferToBank(String username, BankAccount bankAccount, double amount) {
        // find user in DB from username
        // make sure user 'balance' >= amount
        // add amount to selected bank account
        // remove amount from user 'balance'
        return true;
    }

    public boolean transferFromBank(String username, BankAccount bankAccount, double amount) {
        // find user in DB from username
        // add amount to user 'balance'
        // remove amount from selected bank account 'money'
        if (bankAccount != null && bankAccount.getMoney() >= amount) {
            return true;
        }
        return false;
    }
}
