package com.stocks.stocks.api;

import com.stocks.stocks.model.User;
import com.stocks.stocks.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("profile")
@RestController
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public User displayProfile(HttpServletRequest request) {
        return profileService.displayProfile(request);
    }

    @PostMapping
    public void editProfile(@RequestBody User user, HttpServletRequest request) {
        profileService.editProfile(user, request);
    }
}
