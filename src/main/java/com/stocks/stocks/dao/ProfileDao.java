package com.stocks.stocks.dao;

import com.stocks.stocks.model.Credentials;
import com.stocks.stocks.model.User;
import org.springframework.stereotype.Repository;

@Repository("profileDao")
public class ProfileDao {
    public User getUser(String username) {
        // find user in DB from username
        // create User using DB
        return new User(new Credentials(username, "abc123"), "12345", "1@mail.com", "forgot", 100, null, null);
    }

    public void setUser(User newUser) {
        // find user in DB from user.credentials.username
        // change user in DB
    }
}
