package org.emall.user.controller;

import org.emall.user.manager.UserManager;
import org.emall.user.model.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gaopeng 2021/2/1
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserManager userManager;

    @GetMapping("/get")
    public UserResponse get(@RequestParam("id") Long id, @AuthenticationPrincipal final User user) {
        return userManager.get(id);
    }
}
