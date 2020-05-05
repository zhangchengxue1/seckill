package com.example.vo;

import com.example.model.Course;

import java.io.Serializable;

public class CourseVo implements Serializable{
	private static final long serialVersionUID = 2637546614806439774L;

	private Course course;
	private int courseStatus = 0;
	private int remainTime = 0;
	
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public int getCourseStatus() {
		return courseStatus;
	}
	public void setCourseStatus(int courseStatus) {
		this.courseStatus = courseStatus;
	}
	public int getRemainTime() {
		return remainTime;
	}
	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}
	
}
