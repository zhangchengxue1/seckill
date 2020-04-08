package com.example.service;

import com.example.model.User;
import com.example.vo.UserVo;

import javax.validation.Valid;

/**
 * Created by think on 2020/3/31.
 */
public interface UserService {
    public User findByUsernameAndPassword(String username, String password);
    public UserVo findByUsername(String username);
    public void regist(@Valid User user);

    void saveUserToRedisByToken(UserVo userVo, String token);

    Object getUserFromRedisByToken(String s);
}
