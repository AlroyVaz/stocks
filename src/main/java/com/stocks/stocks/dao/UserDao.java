package com.stocks.stocks.dao;


import com.stocks.stocks.model.BankAccount;
import com.stocks.stocks.model.Credentials;
import com.stocks.stocks.model.Stock;
import com.stocks.stocks.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

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

    public User getUser(String currentIdString) {
        if (currentIdString != null) {
            ObjectId currentId = new ObjectId(currentIdString);
            User user = mongoTemplate.findById(currentId, User.class);

            // make sure user exists
            if (user == null)
                return null;

            // get bank accounts
            Query query = new Query();
            query.addCriteria(Criteria.where("userId").is(currentId));
            user.setBankAccountList(mongoTemplate.find(query, BankAccount.class));

            // get stocks
            user.setStockList(mongoTemplate.find(query, Stock.class));

            return user;
        }
        return null;
    }

    public void setUser(User changedUser, String currentIdString) {
        if (currentIdString != null && changedUser != null) {
            ObjectId currentId = new ObjectId(currentIdString);
            if (!currentId.equals(changedUser.getId()))
                return;

            // get user from DB
            User oldUser = mongoTemplate.findById(currentId, User.class);

            // balance should not be changed here and credentials/forgotPassword also should not be null
            if (oldUser == null || oldUser.getBalance() != changedUser.getBalance()
                    || changedUser.getCredentials() == null
                    || changedUser.getForgotPassword() == null)
                return;

            // not stored in UserCollection
            changedUser.setBankAccountList(null);
            changedUser.setStockList(null);

            // update user
            mongoTemplate.save(changedUser);
        }
    }
}
