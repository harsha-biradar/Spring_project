package com.jsp.CourseHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.CourseHub.entity.Enrollement;

import java.util.Optional;

public interface EnrollementRepository extends JpaRepository<Enrollement, Integer> {
	Optional<Enrollement> findByStudentIdAndCourseId(int studentId, int courseId);

	java.util.List<Enrollement> findByStudentId(int studentId);
}

