package com.simian.test_security2.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.simian.test_security2.pojo.User;
import com.simian.test_security2.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    // 私钥
    private static final String SECRET = "security";

    @Autowired
    RedisService redisService;

    @RequestMapping("/success")
    public Map<String, Object> success(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        String token = JWT.create()
                .withClaim("username", user.getUsername())
                .withClaim("userId", user.getId())
                .sign(algorithm);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("userId", user.getId());
        resultMap.put("userName", user.getUsername());

        //
        /*ValueOperations<String, String> ops1 = stringRedisTemplate.opsForValue();
        ops1.set(user.getId().toString(), token);*/

        redisService.set(user.getId().toString(), token);

        return resultMap;
    }

    @RequestMapping("/failure")
    public Map<String, Object> failure(){
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        map.put("msg", "信息错误");
        return map;
    }
}
