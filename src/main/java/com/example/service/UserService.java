package com.example.service;

import com.example.model.User;

import javax.validation.Valid;

/**
 * Created by think on 2020/3/31.
 */
public interface UserService {
    public User findByUsernameAndPassword(String username, String password);
    public User findByUsername(String username);
    public void regist(@Valid User user);

}
