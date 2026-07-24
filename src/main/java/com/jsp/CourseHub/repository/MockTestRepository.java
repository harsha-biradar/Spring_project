package com.jsp.CourseHub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.CourseHub.entity.MockTest;

public interface MockTestRepository extends JpaRepository<MockTest, Integer> {
	List<MockTest> findByCourseId(int courseId);
}
