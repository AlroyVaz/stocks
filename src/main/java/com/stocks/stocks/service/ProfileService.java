package com.stocks.stocks.service;

import com.stocks.stocks.dao.ProfileDao;
import com.stocks.stocks.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class ProfileService {

    private final ProfileDao profileDao;

    @Autowired
    public ProfileService(@Qualifier("profileDao") ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    public User displayProfile(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            Object username = session.getAttribute("USERNAME");
            if (username != null)
                return profileDao.getUser(username.toString());
        }
        return null;
    }

    public void editProfile(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("USERNAME") != null)
            profileDao.setUser(user);
    }
}
