package com.stocks.stocks.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class LogoutService {
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null)
            session.invalidate();
    }
}
