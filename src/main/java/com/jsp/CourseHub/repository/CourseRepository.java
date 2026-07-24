package com.jsp.CourseHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.CourseHub.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

}
