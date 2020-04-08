package com.example.controller;

import com.example.service.UserService;
import com.example.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by think on 2020/3/31.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    UserService userService;

    @RequestMapping("/findUser")
    public UserVo findUser(String userName){
        return userService.findByUsername("11");
    }




}
