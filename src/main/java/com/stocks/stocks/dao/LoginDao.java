package com.stocks.stocks.dao;


import com.stocks.stocks.model.Credentials;
import org.springframework.stereotype.Repository;


@Repository("loginDao")
public class LoginDao {
    // Change for DB
    private static Credentials[] DB = {new Credentials("un", "pw"), new Credentials("root", "root1")};

    public boolean validateUser(Credentials credentials) {
        String password = credentials.getPassword();
        for (Credentials value : DB) {
            if (value.getUsername().equals(credentials.getUsername())) {
                return value.getPassword().equals(password);
            }
        }
        return false;
    }
}
