package com.stocks.stocks.service;

import com.stocks.stocks.dao.UserDao;
import com.stocks.stocks.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class LoginService {

    private final UserDao userDao;

    @Autowired
    public LoginService(@Qualifier("userDao") UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean login(Credentials credentials, HttpServletRequest request) {
        HttpSession session = request.getSession();
        // can't log in if already logged in (must log out first)
        if (session == null || session.getAttribute("USERNAME") == null) {
            String userId = userDao.validateUser(credentials);
            if (userId != null) {
                session.setAttribute("USERNAME", credentials.getUsername());
                session.setAttribute("USER_ID", userId);
                return true;
            }
        }
        return false;
    }
}
