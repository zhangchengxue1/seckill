package com.example.util;

import com.example.model.Course;
import com.example.vo.CourseVo;

/**
 * Created by think on 2020/5/5.
 */
public class CourseUtil {
    public static final int COURSE_NOT_START = 0;
    public static final int COURSE_PROCESSING = 1;
    public static final int COURSE_COMPLETE = 2;

    public static CourseVo courseToCourseVo(Course course){
        CourseVo courseVO = new CourseVo();
        courseVO.setCourse(course);

        //课程状态 剩余时间
        long startTime = course.getStartTime().getTime();
        long endTime = course.getEndTime().getTime();
        long now = System.currentTimeMillis();

        int courseStatus = COURSE_NOT_START;
        int remainTime = 0;

        if(now < startTime){
            courseStatus = COURSE_NOT_START;
            remainTime = (int) ((startTime - now)/1000);
        }else if(now > endTime){
            courseStatus = COURSE_COMPLETE;
            remainTime = -1;
        }else{
            courseStatus = COURSE_PROCESSING;
            remainTime = -1;
        }
        courseVO.setCourseStatus(courseStatus);
        courseVO.setRemainTime(remainTime);
        return courseVO;
    }



}
