package com.example.service.impl;

import com.example.model.Orders;
import com.example.repository.OrderRepository;
import com.example.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by think on 2020/6/7.
 */
@Service
public class OrderServiceImpl implements IOrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Orders getOrderByUsernameAndCourseNo(String username, String courseNo) {
        return orderRepository.findByUsernameAndCourseNo(username,courseNo);
    }

    @Override
    public Orders saveAndFlush(Orders orders) {
        return orderRepository.saveAndFlush(orders);
    }

}
