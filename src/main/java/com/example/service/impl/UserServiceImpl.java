package com.example.service.impl;

import com.example.model.User;
import com.example.repository.UserRpository;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

/**
 * Created by think on 2020/3/31.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRpository userRpository;

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return userRpository.findByUsernameAndPassword( username,  password);
    }

    @Override
    public User findByUsername(String username) {
        return userRpository.findByUsername(username);
    }

    @Override
    public void regist(@Valid User user) {
        userRpository.save(user);
    }
}
