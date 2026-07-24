package com.jsp.CourseHub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsp.CourseHub.entity.Module;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
	
	List<Module> findByCourseId(int courseId);
	
	List<Module> findByCourseIdOrderByOrderIndexAsc(int courseId);
	
	@Query("SELECT COUNT(m) FROM Module m WHERE m.course.id = :courseId")
	int countByCourseId(int courseId);
}
