package com.jsp.CourseHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.CourseHub.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
