package com.stocks.stocks.service;

import com.stocks.stocks.dao.UserDao;
import com.stocks.stocks.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class ProfileService {

    private final UserDao userDao;

    @Autowired
    public ProfileService(@Qualifier("userDao") UserDao userDao) {
        this.userDao = userDao;
    }

    public User displayProfile(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            String userId = (String)session.getAttribute("USER_ID");
            if (userId != null)
                return userDao.getUser(userId);
        }
        return null;
    }

    public void editProfile(User changedUser, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            String userId = (String)session.getAttribute("USER_ID");
            if (userId != null)
                userDao.setUser(changedUser, userId);
        }
    }
}
