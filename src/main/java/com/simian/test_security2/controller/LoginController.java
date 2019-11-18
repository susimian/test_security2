package com.simian.test_security2.controller;

import com.simian.test_security2.pojo.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @RequestMapping("/success")
    public Map<String, Object> success(){
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        map.put("msg", principal);
        return map;
    }

    @RequestMapping("/failure")
    public Map<String, Object> failure(){
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        map.put("msg", "信息错误");
        return map;
    }
}
