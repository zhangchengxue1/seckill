package com.example.redis;

import com.example.model.Course;
import org.springframework.stereotype.Repository;

/**
 * Created by think on 2020/5/5.
 */
@Repository
public class CourseRedis extends IRedisDao<Course> {

    private static final String REDIS_KEY = "com.dayup.seckil.redis.CourseRedis";

    @Override
    protected String getRedisKey() {
        return REDIS_KEY;
    }
}
