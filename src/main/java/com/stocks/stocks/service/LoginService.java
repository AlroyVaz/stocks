package com.stocks.stocks.service;

import com.stocks.stocks.dao.LoginDao;
import com.stocks.stocks.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class LoginService {

    private final LoginDao loginDao;

    @Autowired
    public LoginService(@Qualifier("loginDao") LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    public boolean login(Credentials credentials, HttpSession session) {
        if(loginDao.validateUser(credentials)) {
            session.setAttribute("USERNAME", credentials.getUsername());
            return true;
        }
        return false;
    }
}
