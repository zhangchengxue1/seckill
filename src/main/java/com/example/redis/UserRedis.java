package com.example.redis;

import com.example.model.User;
import com.google.gson.Gson;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * Created by think on 2020/4/7.
 */
@Repository
public class UserRedis extends IRedisDao<User> {

    private static final String REDIS_KEY="com.example.redis.UserRedis";
    @Override
    protected String getRedisKey() {
        return REDIS_KEY;
    }

    public void add(String key, Long time, User user){
        Gson gson= new Gson();
        redisTemplate.opsForValue().set(key,gson.toJson(user),time, TimeUnit.SECONDS);
    };


}
