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

    public ObjectId validateUser(Credentials credentials) {
        if (credentials != null) {
            Query query = new Query();
            query.addCriteria(Criteria.where("credentials.username").is(credentials.getUsername()));
            User user = mongoTemplate.findOne(query, User.class);
            if (user != null && user.getCredentials().getPassword().equals(credentials.getPassword()))
                return user.getId();
        }
        return null;
    }

    public User getUser(ObjectId currentId) {
        if (currentId != null) {
            User user = mongoTemplate.findById(currentId, User.class);

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

    public void setUser(User changedUser, ObjectId currentId) {
        if (changedUser != null && currentId != null) {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(currentId));
            mongoTemplate.findAndReplace(query, changedUser);
        }
    }
}
