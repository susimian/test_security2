package com.simian.test_security2.service;

import com.simian.test_security2.dao.UserDao;
import com.simian.test_security2.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDao.findByUserName(userName);
        if (user == null){
            throw new UsernameNotFoundException("账户不存在");
        }
        return user;
    }

    public User save(User user){
        user = userDao.save(user);
        return user;
    }

    public User login(User user){
        User dataUser = userDao.findByUserName(user.getUsername());
        if (dataUser.getPassword().equals(user.getPassword())){
            return dataUser;
        }
        return user;
    }

    public User getById(Long id){
        return userDao.findById(id).get();
    }


}
