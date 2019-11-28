package com.stocks.stocks.dao;


import com.stocks.stocks.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserCollection extends MongoRepository<User, ObjectId> {
    Optional<User> findById(ObjectId id);
}
