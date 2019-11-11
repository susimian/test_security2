package com.simian.test_security2.controller;

import com.simian.test_security2.pojo.User;
import com.simian.test_security2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/register")
    public User save(User user){
        return userService.save(user);
    }

    @RequestMapping("/admin/hello")
    public String admin(){
        return "hello admin";
    }

    @RequestMapping("/user/hello")
    public String user(){
        return "hello user";
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
