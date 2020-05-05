package com.example.repository;

import com.example.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by think on 2020/5/5.
 */
public interface CourseRepository extends JpaRepository<Course, String> {

    @Modifying
    @Query("update Course c set stockQuantity = stockQuantity - 1 where courseNo = :courseNo and stockQuantity > 0")
    public int reduceStockByCourseNo(@Param(value = "courseNo") String courseNo);
}
