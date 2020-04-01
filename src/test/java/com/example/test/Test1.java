package com.example.test;

import com.example.model.User;
import com.example.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * Created by think on 2020/3/31.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Test1 {

    @Autowired
    public UserService userService;

    @Test
    public User findUser(){
        User user=userService.findByUsername("11");
        System.out.println(user.getUsername()+"  "+user.getPassword());
        return user;
    }

   /* public static void main(String[] args) {
        User user=userService.findByUsername("11");
        System.out.println(user.getUsername()+"  "+user.getPassword());
    }*/

}
