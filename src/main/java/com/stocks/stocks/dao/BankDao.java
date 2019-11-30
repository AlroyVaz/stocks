package com.stocks.stocks.dao;

import com.stocks.stocks.model.BankAccount;
import com.stocks.stocks.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bankDao")
public class BankDao {
    private final MongoTemplate mongoTemplate;

    public BankDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void addBank(List<BankAccount> bankAccountList, String userIdString) {
        if (bankAccountList != null && userIdString != null
                && bankAccountList.size() > 0
                && ObjectId.isValid(userIdString)) {
            ObjectId userId = new ObjectId(userIdString);
            for (BankAccount b : bankAccountList) {
                Query query = new Query();
                query.addCriteria(Criteria.where("bankRoutingNumber").is(b.getBankRoutingNumber())
                        .and("bankAccountNumber").is(b.getBankAccountNumber())
                        .and("userId").is(userId));
                if (!mongoTemplate.exists(query, BankAccount.class)) {
                    b.setUserId(userId);
                    mongoTemplate.insert(b);
                }
            }
        }
    }

    public void transferToBank(String bankIdString, double amount, String userIdString) {
        if (bankIdString != null && userIdString != null && ObjectId.isValid(bankIdString) && ObjectId.isValid(userIdString)) {
            ObjectId bankId = new ObjectId(bankIdString);
            ObjectId userId = new ObjectId(userIdString);

            // find user in DB
            User user = mongoTemplate.findById(userId, User.class);
            BankAccount bankAccount = mongoTemplate.findById(bankId, BankAccount.class);

            // make sure user exists, bankAccount exists, and user has enough money
            if (user == null || bankAccount == null
                    || (!bankAccount.getUserId().equals(userId))
                    || user.getBalance() < amount)
                return;

            // add amount to selected bank account
            bankAccount.setMoney(bankAccount.getMoney() + amount);
            mongoTemplate.save(bankAccount);

            // remove amount from user's balance
            user.setBalance(user.getBalance() - amount);
            mongoTemplate.save(user);
        }
    }

    public void transferFromBank(String bankIdString, double amount, String userIdString) {
        if (bankIdString != null && userIdString != null && ObjectId.isValid(bankIdString) && ObjectId.isValid(userIdString)) {
            ObjectId bankId = new ObjectId(bankIdString);
            ObjectId userId = new ObjectId(userIdString);

            // find user in DB
            User user = mongoTemplate.findById(userId, User.class);
            BankAccount bankAccount = mongoTemplate.findById(bankId, BankAccount.class);

            // make sure user exists, bankAccount exists, and bankAccount has enough money
            if (user == null || bankAccount == null
                    || (!bankAccount.getUserId().equals(userId))
                    || bankAccount.getMoney() < amount)
                return;

            // add amount to user's balance
            user.setBalance(user.getBalance() + amount);
            mongoTemplate.save(user);

            // remove amount from selected bank account
            bankAccount.setMoney(bankAccount.getMoney() - amount);
            mongoTemplate.save(bankAccount);
        }
    }
}
