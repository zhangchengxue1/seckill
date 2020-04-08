package com.example.controller;

import com.example.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by think on 2020/4/7.
 */
@Controller
public class HomeController {

    @RequestMapping(value="/home", method= RequestMethod.GET)
    public String home(Model model, User user){
        System.out.println("-----------------"+user.getUsername());
        model.addAttribute("user",user);
        return "home";
    }


}
