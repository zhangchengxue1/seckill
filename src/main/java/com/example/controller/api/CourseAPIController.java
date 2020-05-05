package com.example.controller.api;

import com.example.base.controller.BaseApiController;
import com.example.base.result.Result;
import com.example.model.Course;
import com.example.service.ICourseService;
import com.example.util.CourseUtil;
import com.example.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by think on 2020/5/5.
 */
@RestController
public class CourseAPIController extends BaseApiController{

    @Autowired
    public ICourseService courseService;

    @RequestMapping(value="courseList",method= RequestMethod.GET)
    public Result<List<Course>> courseList(){
        return Result.success(courseService.findAllCourses());
    }

    @RequestMapping(value="courseDetail/{courseNo}",method=RequestMethod.GET)
    public Result<CourseVo> courseDetail(@PathVariable String courseNo){
        return Result.success(CourseUtil.courseToCourseVo(courseService.findCourseByCourseNo(courseNo)));
    }



}
