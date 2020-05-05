package com.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.model.Course;
import com.example.redis.CourseRedis;
import com.example.repository.CourseRepository;
import com.example.service.ICourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by think on 2020/5/5.
 */
@Service
@Transactional
public class CourseServiceImpl implements ICourseService{

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseRedis courseRedis;
    public static final String ALL_COURSE_REDIS="allCourseRedis";

    @Override
    public List<Course> findAllCourses() {
        List<Course> courseList = new ArrayList<>();
        //redis 中读取数据
        String courseListString = (String) courseRedis.getString(ALL_COURSE_REDIS);
        courseList = JSON.parseArray(courseListString, Course.class);
        //mysql中读取数据
        if(StringUtils.isEmpty(courseListString)){
            //读数据库
            courseList = courseRepository.findAll();
            //缓存到redis中
            String coursesString = JSON.toJSONString(courseList);
            courseRedis.putString(ALL_COURSE_REDIS, coursesString, -1);
        }

        return courseList;
    }

    @Override
    public Course findCourseByCourseNo(String courseNo) {
        Optional<Course> course = courseRepository.findById(courseNo);
        return course.orElse(null);//course.isPresent()?user.get():null
    }

    @Override
    public int reduceStockByCourseNo(String courseNo) {

        return courseRepository.reduceStockByCourseNo(courseNo);
    }
}
