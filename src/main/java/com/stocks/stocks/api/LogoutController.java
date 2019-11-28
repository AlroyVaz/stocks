package com.stocks.stocks.api;

import com.stocks.stocks.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("logout")
@RestController
public class LogoutController {
    private final LogoutService logoutService;

    @Autowired
    public LogoutController(LogoutService logoutService) {
        this.logoutService = logoutService;
    }

    @GetMapping
    public boolean logout(HttpServletRequest request) {
            return logoutService.logout(request);
    }
}
