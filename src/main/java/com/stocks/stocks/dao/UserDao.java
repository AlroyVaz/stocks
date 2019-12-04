package com.stocks.stocks.dao;

import com.stocks.stocks.model.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("userDao")
public class UserDao {
    private final MongoTemplate mongoTemplate;

    public UserDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public boolean addUser(User user) {
        if (user != null) {
            if (mongoTemplate.findById(user.getId(), User.class) != null)
                return false; // user id already exists

            if (user.getCredentials() == null || user.getCredentials().getUsername() == null || user.getCredentials().getPassword() == null || user.getForgotPassword() == null)
                return false; // user credentials/forgotPassword don't exist or are invalid

            Query query = new Query();
            query.addCriteria(Criteria.where("credentials.username").is(user.getCredentials().getUsername()));
            if (mongoTemplate.find(query, User.class).size() > 0)
                return false; // another user has the same username

            if (user.getBalance() != 0)
                return false; // user balance isn't initially zero

            // not stored in UserCollection (should be null on registration)
            user.setBankAccountList(null);
            user.setStockList(null);
            user.setStockIdList(null);
            user.setScheduleList(null);

            // add user to db
            mongoTemplate.save(user);
            return true;
        }
        return false;
    }

    public String validateUser(Credentials credentials) {
        if (credentials != null) {
            Query query = new Query();
            query.addCriteria(Criteria.where("credentials.username").is(credentials.getUsername()));
            User user = mongoTemplate.findOne(query, User.class);
            if (user != null && user.getCredentials().getPassword().equals(credentials.getPassword()))
                return user.getId().toString();
        }
        return null;
    }

    public User getUser(String userIdString) {
        if (userIdString != null) {
            ObjectId userId = new ObjectId(userIdString);
            User user = mongoTemplate.findById(userId, User.class);

            // make sure user exists
            if (user == null)
                return null;

            // get bank accounts
            Query query = new Query();
            query.addCriteria(Criteria.where("userId").is(userIdString));
            user.setBankAccountList(mongoTemplate.find(query, BankAccount.class));

            // get stocks
            List<Stock> stockList = new ArrayList<Stock>();
            for (String stockIdString : user.getStockIdList()) {
                if (!ObjectId.isValid(stockIdString))
                    return null;
                ObjectId stockId = new ObjectId(stockIdString);
                Stock stock = mongoTemplate.findById(stockId, Stock.class);
                if (stock == null)
                    return null;
                stockList.add(stock);
            }
            user.setStockList(stockList);

            //get schedules
            user.setScheduleList(mongoTemplate.find(query, Schedule.class));

            return user;
        }
        return null;
    }

    public void updateUser(User changedUser, String userIdString) {
        if (userIdString != null && changedUser != null) {
            ObjectId userId = new ObjectId(userIdString);
            if (!userId.equals(changedUser.getId()))
                return;

            // get user from DB
            User oldUser = mongoTemplate.findById(userId, User.class);

            // balance should not be changed here and credentials/forgotPassword also should not be null
            if (oldUser == null || oldUser.getBalance() != changedUser.getBalance()
                    || changedUser.getCredentials() == null
                    || changedUser.getCredentials().getUsername() == null
                    || changedUser.getCredentials().getPassword() == null
                    || changedUser.getForgotPassword() == null)
                return;

            // make sure username is unique
            Query query = new Query();
            query.addCriteria(Criteria.where("credentials.username").is(changedUser.getCredentials().getUsername()));
            if (mongoTemplate.find(query, User.class).size() > 0)
                return; // another user has the same username

            // stock id list should not be changed
            changedUser.setStockIdList(oldUser.getStockIdList());

            // not stored in UserCollection
            changedUser.setBankAccountList(null);
            changedUser.setStockList(null);
            changedUser.setScheduleList(null);

            // update user
            mongoTemplate.save(changedUser);
        }
    }

    public boolean forgotPassword(String username, String forgotPassword) {
        if (username != null && forgotPassword != null) {
            // find user in db
            Query query = new Query();
            query.addCriteria(Criteria.where("credentials.username").is(username));
            User user = mongoTemplate.findOne(query, User.class);

            // ensure user exists
            if (user == null)
                return false;

            // check if forgot password secret is correct
            return user.getForgotPassword().equals(forgotPassword);
        }
        return false;
    }
}
