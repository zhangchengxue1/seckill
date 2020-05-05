package com.example.service;

import com.example.model.Course;

import java.util.List;

/**
 * Created by think on 2020/5/5.
 */
public interface ICourseService {

    public List<Course> findAllCourses();

    public Course findCourseByCourseNo(String courseNo);

    public int reduceStockByCourseNo(String courseNo);


}
