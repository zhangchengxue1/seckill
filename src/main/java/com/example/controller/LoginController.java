package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import com.example.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by think on 2020/4/1.
 */
@Controller
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String toLogin(Model model){
        User user=new User();
        model.addAttribute("user",user);
        model.addAttribute("title","登入页面");
        return "login";
    }

    @PostMapping("/login")
    public String register(@ModelAttribute(value="user") @Valid User user ,BindingResult bindingResult,
                           HttpSession session){

        log.info("username="+user.getUsername()+";password="+user.getPassword());
        log.info("一次加密 password="+MD5Util.inputToBack(user.getPassword()));

        if(bindingResult.hasErrors()){
            return "login";
        }
        User userDb=userService.findByUsername(user.getUsername());
        if(userDb!=null){
            String inputPwd=MD5Util.inputToDb(user.getPassword(),userDb.getDbflag());
            log.info("----inputPwd="+inputPwd);
            if(userDb.getPassword().equals(inputPwd)){
                session.setAttribute("user",userDb);
                return "home";
            }else{
                return "login";
            }
        }else {
            return "login";
        }
    }



}
