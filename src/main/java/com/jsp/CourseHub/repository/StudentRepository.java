package com.jsp.CourseHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.CourseHub.entity.Student;

public interface StudentRepository  extends JpaRepository<Student, Integer>{

}
