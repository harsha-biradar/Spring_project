package com.jsp.CourseHub.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.CourseHub.entity.Category;
import com.jsp.CourseHub.exception.CategoryNotFoundException;
import com.jsp.CourseHub.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;

	public Category saveCategory(Category category) {

		return categoryRepository.save(category);
	}

	public List<Category> getAllCategories() {

		return categoryRepository.findAll();
	}

	public Category getCategoryById(int id) {

		return categoryRepository.findById(id)
				.orElseThrow(() ->
				new CategoryNotFoundException("Category Not Found"));
	}

	public Category deleteCategory(int id) {

		Category category = getCategoryById(id);

		categoryRepository.delete(category);

		return category;
	}

	public Category updateCategory(int id, Category categoryDetails) {
		Category category = getCategoryById(id);
		category.setCategoryName(categoryDetails.getCategoryName());
		category.setDescription(categoryDetails.getDescription());
		return categoryRepository.save(category);
	}
}
