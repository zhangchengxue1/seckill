package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import com.example.util.MD5Util;
import com.example.util.ValidateCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

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
                           HttpSession session,Model model,String code){

        log.info("username="+user.getUsername()+";password="+user.getPassword());
        if(bindingResult.hasErrors()){
            return "login";
        }
        String sessionCode = (String) session.getAttribute("code");
        if(!StringUtils.equalsIgnoreCase(code, sessionCode)){
            model.addAttribute("message", "验证码不匹配");
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

    @RequestMapping("/validateCode")
    public void validate(HttpServletRequest request, HttpServletResponse response ) throws IOException {
        response.setContentType("image/jpeg");
        //禁止图像缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires",0);//在代理服务器端防止缓冲
        HttpSession session = request.getSession();
        ValidateCode validateCode = new ValidateCode(120, 34, 5, 100);
        session.setAttribute("code", validateCode.getCode());
        validateCode.write(response.getOutputStream());
    }


}
