package com.example.service.impl;

import com.example.model.User;
import com.example.redis.UserRedis;
import com.example.repository.UserRpository;
import com.example.service.UserService;
import com.example.vo.UserVo;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    UserRedis userRedis;

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return userRpository.findByUsernameAndPassword( username,  password);
    }

    @Override
    public UserVo findByUsername(String username) {
        UserVo userVo=new UserVo();
        User user=userRedis.get(username);
        if(user==null){
            user=userRpository.findByUsername(username);
            if(user != null){
                userRedis.put(user.getUsername(),user,-1);
            }else{
                return null;
            }
        }
        BeanUtils.copyProperties(user , userVo);
        return userVo;
    }

    @Override
    public void regist(@Valid User user) {
        userRpository.save(user);
    }


    @Override
    public void saveUserToRedisByToken(UserVo userVo, String token) {
       User user = new User();
       BeanUtils.copyProperties(userVo,user);
       userRedis.put(token , user ,3600);
    }

    @Override
    public Object getUserFromRedisByToken(String s) {
        return userRedis.get(s);
    }
}
