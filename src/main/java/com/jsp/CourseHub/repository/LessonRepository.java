package com.jsp.CourseHub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.CourseHub.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
	
	List<Lesson> findByModuleId(int moduleId);
	
	List<Lesson> findByModuleIdOrderByOrderIndexAsc(int moduleId);
}

