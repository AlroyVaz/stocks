package com.stocks.stocks.api;

import com.stocks.stocks.model.Credentials;
import com.stocks.stocks.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("login")
@RestController
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public void login(@RequestBody Credentials credentials, HttpServletRequest request) {
        loginService.login(credentials, request);
    }

    @PostMapping("/{username}/forgotPassword")
    public void forgotPassword(@PathVariable("username") String username,@RequestBody String forgotPassword, HttpServletRequest request) {
        loginService.forgotPassword(username, forgotPassword, request);
    }
}
