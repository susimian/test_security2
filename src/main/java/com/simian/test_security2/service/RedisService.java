package com.simian.test_security2.service;

import org.springframework.stereotype.Service;

@Service
public class RedisService {
    public String get(Integer userId){
        return "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW4ifQ.zWa8SJLeyIpfZUAqiNne2b4jYSzmOG0iDzldlDNAANk";
    }
}
