package com.stocks.stocks.dao;


import com.stocks.stocks.model.BinarySearch;
import com.stocks.stocks.model.Credentials;
import com.stocks.stocks.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("loginDao")
public class LoginDao {
    private final UserCollection userCollection;

    public LoginDao(UserCollection userCollection) {
        this.userCollection = userCollection;
    }

    public ObjectId validateUser(Credentials credentials) {
        if (credentials != null) {
            List<User> users = userCollection.findAll(new Sort(Sort.Direction.ASC, "credentials.username"));
            int index = BinarySearch.binarySearch(users, credentials.getUsername());
            if (index != -1 && users.get(index).getCredentials().getPassword().equals(credentials.getPassword())) {
                return users.get(index).getId();
            }
        }
        return null;
    }
}
