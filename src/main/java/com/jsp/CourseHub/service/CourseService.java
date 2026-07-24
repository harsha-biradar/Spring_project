package com.jsp.CourseHub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.CourseHub.entity.Category;
import com.jsp.CourseHub.entity.Course;
import com.jsp.CourseHub.entity.Trainer;
import com.jsp.CourseHub.exception.CourseNotFoundException;
import com.jsp.CourseHub.repository.CourseRepository;

@Service
public class CourseService {
	@Autowired
	CourseRepository courseRepository;

	@Autowired
	CategoryService categoryService;

	@Autowired
	TrainerService trainerService;

	public Course saveCourse(Course course, int categoryId, int trainerId) {

		Category category = categoryService.getCategoryById(categoryId);

		Trainer trainer = trainerService.getTrainerById(trainerId);

		course.setCategory(category);

		course.setTrainer(trainer);

		return courseRepository.save(course);
	}

	public List<Course> getAllCourses() {

		return courseRepository.findAll();
	}

	public Course getCourseById(int id) {

		return courseRepository.findById(id)
				.orElseThrow(() ->
				new CourseNotFoundException("Course Not Found"));
	}

	public Course deleteCourse(int id) {

		Course course = getCourseById(id);

		courseRepository.delete(course);

		return course;
	}

	public Course updateCourse(int id, Course courseDetails, int categoryId, int trainerId) {
		Course course = getCourseById(id);
		
		if (categoryId > 0) {
			Category category = categoryService.getCategoryById(categoryId);
			course.setCategory(category);
		}
		
		if (trainerId > 0) {
			Trainer trainer = trainerService.getTrainerById(trainerId);
			course.setTrainer(trainer);
		}
		
		course.setTitle(courseDetails.getTitle());
		course.setDiscription(courseDetails.getDiscription());
		course.setPrice(courseDetails.getPrice());
		course.setDuration(courseDetails.getDuration());
		
		return courseRepository.save(course);
	}

}
