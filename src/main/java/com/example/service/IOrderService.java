package com.example.service;

import com.example.model.Orders;

/**
 * Created by think on 2020/6/7.
 */
public interface IOrderService {

    Orders getOrderByUsernameAndCourseNo(String username, String courseNo);

    Orders saveAndFlush(Orders orders);
}
