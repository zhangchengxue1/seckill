package com.example.service.impl;

import com.example.base.result.Result;
import com.example.base.result.ResultCode;
import com.example.model.Course;
import com.example.model.Orders;
import com.example.model.User;
import com.example.redis.CourseRedis;
import com.example.service.ICourseService;
import com.example.service.IOrderService;
import com.example.service.ISeckillService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by think on 2020/6/7.
 */
@Service
@Transactional
public class SeckillServiceImpl implements ISeckillService {


    @Autowired
    private ICourseService courseService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    public CourseRedis courseRedis;
    @Autowired
    public KafkaTemplate<String, String > kafkaTempalte;

    private static Map<String, Boolean> isSeckill = new HashMap<String, Boolean>();

    public Orders seckill(User user, Course course){
        //减库存
        int success = courseService.reduceStockByCourseNo(course.getCourseNo());
        //下订单
        if(success>0){
            Orders orders = new Orders();
            BeanUtils.copyProperties(course, orders);
            orders.setUsername(user.getUsername());
            orders.setCreatBy(user.getUsername());
            orders.setCreateDate(new Date());
            orders.setPayPrice(course.getCourcePrice());
            orders.setPayStatus("0");
            return orderService.saveAndFlush(orders);
        }
        return  null;
    }

    @Override
    public Result<Orders> seckillResult(User user, String courseNo) {
        Orders orders = orderService.getOrderByUsernameAndCourseNo(user.getUsername(), courseNo);
        if(orders == null){
            return Result.failure(ResultCode.SECKILL_LINE_UP);
        }
        return Result.success(orders);
    }

    ;

    @Override
    public Result<Orders> seckillFlow(User user, String courseNo) {
        System.out.println(" user = "+user.getUsername());
        boolean isPass = isSeckill.get(courseNo);
        if(isPass){
            return Result.failure(ResultCode.SECKILL_NO_QUOTE);
        }
        //预减库存
        double stockQuantity = courseRedis.decr(courseNo, -1);
        if(stockQuantity <= 0){
            isSeckill.put(courseNo, true);
            return Result.failure(ResultCode.SECKILL_NO_QUOTE);
        }
        //判断库存
       /* Course course = courseService.findCourseByCourseNo(courseNo);
        int stock = courseService.reduceStockByCourseNo(courseNo);*/
        //判断库存redis 预减库存
        /*if(stock <= 0){
          //  isSeckill.put(courseNo, true);
            return Result.failure(ResultCode.SECKILL_NO_QUOTE);
        }*/
        //判断是否购买
        Orders order = orderService.getOrderByUsernameAndCourseNo(user.getUsername(), courseNo);
        if(order != null){
            return Result.failure(ResultCode.SECKILL_BOUGHT);
        }

        //减库存
        // Orders newOrders =seckill(user,course);
        // return Result.success(newOrders);
        kafkaTempalte.send("test",courseNo+","+user.getUsername());
        //Orders newOrder = seckill(user, course);
        return Result.failure(ResultCode.SECKILL_LINE_UP);
    }

    @Override
    public void cacheAllCourse() {
        List<Course> courseList = courseService.findAllCourses();
        if(courseList == null){
            return;
        }
        for(Course course : courseList){
            courseRedis.putString(course.getCourseNo(), course.getStockQuantity(), 60, true);
            courseRedis.put(course.getCourseNo(), course, -1);
            isSeckill.put(course.getCourseNo(), false);
        }
    }
}
