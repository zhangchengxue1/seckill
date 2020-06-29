package com.example.kafka;

import com.example.model.Course;
import com.example.model.Orders;
import com.example.model.User;
import com.example.service.ICourseService;
import com.example.service.IOrderService;
import com.example.service.ISeckillService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created by think on 2020/6/29.
 */
@Component
public class KafkaConsumer {
    @Autowired
    public ICourseService courseService;

    @Autowired
    public IOrderService orderService;

    @Autowired
    public ISeckillService seckillService;

    @KafkaListener(id="seconds-kill", topics = "test", groupId = "seconds-kill")
    public void listener(ConsumerRecord<?, ?> record) {
        String[] messages = record.value().toString().split(",");
        String courseNo  = messages[0];
        String username  = messages[1];

        Course course = courseService.findCourseByCourseNo(courseNo);
        int stock = course.getStockQuantity();
        if(stock <= 0){
            return ;
        }
        //判断是否已经购买
        Orders order = orderService.getOrderByUsernameAndCourseNo(username, courseNo);
        if(order != null){
            return ;
        }
        //减库存 下订单
        User user = new User();
        user.setUsername(username);
        seckillService.seckill(user, course);
    }




}
