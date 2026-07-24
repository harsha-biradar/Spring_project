package com.jsp.CourseHub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsp.CourseHub.entity.MockTestAttempt;

public interface MockTestAttemptRepository extends JpaRepository<MockTestAttempt, Integer> {
	List<MockTestAttempt> findByStudentId(int studentId);
	
	@Query("SELECT DISTINCT mta.mockTest FROM MockTestAttempt mta WHERE mta.student.id = :studentId AND mta.mockTest.course.id = :courseId")
	List<com.jsp.CourseHub.entity.MockTest> findAttemptedMockTestsByStudentAndCourse(int studentId, int courseId);
}
