package com.example.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by think on 2020/4/7.
 */
public abstract class IRedisDao<T> {


    @Resource
    protected HashOperations<String , String ,T > hashOperations;

    @Autowired
    protected RedisTemplate<String,Object> redisTemplate;
    @Resource
    protected ValueOperations<String, Object> valueOperations;

    /**
     * 获取一个值
     * @return
     */
    protected abstract String getRedisKey();


    /**
     *
     * @param key
     * @param doamin
     * @param expire 过期时间 单位 秒 -1表示不设置过期时间
     */
    public void put(String key, T doamin, long expire) {
        hashOperations.put(getRedisKey(), key, doamin);
        if(expire!= -1) {
            redisTemplate.expire(getRedisKey(), expire, TimeUnit.SECONDS);
        }
    }

    /***
     *  删除**
     * @paramkey 传入key的名称
     * */
    public void remove(String key) {
        hashOperations.delete(getRedisKey(), key);
    }

    /**
     * 根据key  查询
     * @param key
     * @return
     */
    public T get(String key){
        return hashOperations.get(getRedisKey(), key);
    }

    /**
     * 获取当前redis所有的对象
     * @return
     */
    public List<T> getAll(){
        return hashOperations.values(getRedisKey());
    }

    /**
     * @return
     */
    public Set<String> getKeys(){
        return hashOperations.keys(getRedisKey());
    }

    /**
     * 判断当前key 是否在redis中
     * @param key
     * @return
     */
    public boolean isKeyExists(String key){
        return hashOperations.hasKey(getRedisKey(),key);
    }

    /**
     *  检查当前key下缓存的数量
     * @return
     */
    public long count(){
        return hashOperations.size(getRedisKey());
    }

    /**
     * 清空redis
     */
    public void empty(){
        Set<String> set = hashOperations.keys(getRedisKey());
        set.stream().forEach(key->{
            hashOperations.delete(getRedisKey(),key);
        });
    }
    public void putString(String key, Object domain, long expire, boolean isToString){
        if(isToString){
            String str = beanToString(domain);
            valueOperations.set(key, str);
        }else{
            valueOperations.set(key, domain);
        }
        if(expire != -1){
            redisTemplate.expire(getRedisKey(), expire, TimeUnit.SECONDS);
        }
    }

    public void putString(String key, Object domain, long expire){
        valueOperations.set(key, domain);
        if(expire != -1){
            redisTemplate.expire(getRedisKey(), expire, TimeUnit.SECONDS);
        }
    }

    public Object getString(String key, Class<T> clazz) {
        String str = (String) valueOperations.get(key);
        return stringToBean(str, clazz);
    }


    public Object getString(String key) {
        return valueOperations.get(key);
    }
    public static <T> String beanToString(T value) {
        if(value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return ""+value;
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }
    }
    @SuppressWarnings("unchecked")
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class) {
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

}
