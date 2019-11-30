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
            query.addCriteria(Criteria.where("userId").is(userId));
            user.setBankAccountList(mongoTemplate.find(query, BankAccount.class));

            // get stocks
            List<Stock> stockList = new ArrayList<Stock>();
            for (ObjectId stockId : user.getStockIdList()) {
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

    public void setUser(User changedUser, String userIdString) {
        if (userIdString != null && changedUser != null) {
            ObjectId userId = new ObjectId(userIdString);
            if (!userId.equals(changedUser.getId()))
                return;

            // get user from DB
            User oldUser = mongoTemplate.findById(userId, User.class);

            // balance should not be changed here and credentials/forgotPassword also should not be null
            if (oldUser == null || oldUser.getBalance() != changedUser.getBalance()
                    || changedUser.getCredentials() == null
                    || changedUser.getForgotPassword() == null)
                return;

            // not stored in UserCollection
            changedUser.setBankAccountList(null);
            changedUser.setStockList(null);
            changedUser.setStockIdList(null);
            changedUser.setScheduleList(null);

            // update user
            mongoTemplate.save(changedUser);
        }
    }
}
