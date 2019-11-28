package com.stocks.stocks.service;

import com.stocks.stocks.dao.LoginDao;
import com.stocks.stocks.model.Credentials;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class LoginService {

    private final LoginDao loginDao;

    @Autowired
    public LoginService(@Qualifier("loginDao") LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    public boolean login(Credentials credentials, HttpServletRequest request) {
        HttpSession session = request.getSession();
        // not already logged in (must log out first)
        if (session != null && session.getAttribute("USERNAME") != null)
            return false;
        ObjectId userId = loginDao.validateUser(credentials);
        if(userId != null) {
            session.setAttribute("USERNAME", credentials.getUsername());
            session.setAttribute("USER_ID", userId);
            return true;
        }
        return false;
    }
}
