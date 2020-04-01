package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import com.example.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by think on 2020/4/1.
 */
@Controller
public class RegisterController {

    private static Logger log = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    UserService userService;

    @GetMapping("/reg")
    public ModelAndView toRegister(){
        User user=new User();
        return new ModelAndView("register").addObject(user);
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute(value="user") @Valid User user ,BindingResult bindingResult){

        log.info("username="+user.getUsername()+";password="+user.getPassword());
        if(bindingResult.hasErrors()){
            return new ModelAndView("register");
        }
        String salt = "alex";
        String newPassword = MD5Util.backtToDb(user.getPassword(), salt);
        user.setId(2018);
        user.setPassword(newPassword);
        user.setDbflag(salt);
        userService.regist(user);
        return new ModelAndView("register");
    }




}
