package com.stocks.stocks.service;

import com.stocks.stocks.dao.UserDao;
import com.stocks.stocks.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class RegistrationService {

    private final UserDao userDao;

    @Autowired
    public RegistrationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void addUser(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("USERNAME") == null)
            userDao.addUser(user);
    }
}
