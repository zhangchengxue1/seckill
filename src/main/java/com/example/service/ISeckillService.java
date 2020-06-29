package com.example.service;

import com.example.base.result.Result;
import com.example.model.Course;
import com.example.model.Orders;
import com.example.model.User;

/**
 * Created by think on 2020/6/7.
 */
public interface ISeckillService {
    Result<Orders> seckillFlow(User user, String courseNo);

    void cacheAllCourse();
    public Orders seckill(User user, Course course);

    Result<Orders> seckillResult(User user, String courseNo);
}

