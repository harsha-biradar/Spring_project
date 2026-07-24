package com.jsp.CourseHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.CourseHub.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
