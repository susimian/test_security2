package com.simian.test_security2.controller;

import com.simian.test_security2.pojo.Role;
import com.simian.test_security2.pojo.User;
import com.simian.test_security2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping("/register")
    public String save(User user, HttpServletRequest request,HttpServletResponse response) throws IOException {
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId(2);
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "注册成功";

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
