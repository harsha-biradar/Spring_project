package com.jsp.CourseHub.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.CourseHub.entity.Course;
import com.jsp.CourseHub.entity.Enrollement;
import com.jsp.CourseHub.entity.Student;
import com.jsp.CourseHub.entity.Trainer;
import com.jsp.CourseHub.repository.CourseProgressRepository;
import com.jsp.CourseHub.repository.EnrollementRepository;
import com.jsp.CourseHub.repository.StudentRepository;
import com.jsp.CourseHub.repository.TrainerRepository;
import com.jsp.CourseHub.repository.UserRepository;
import com.jsp.CourseHub.entity.User;

@Service
public class AdminDashboardService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	TrainerRepository trainerRepository;

	@Autowired
	EnrollementRepository enrollementRepository;

	@Autowired
	CourseProgressRepository courseProgressRepository;

	@Autowired
	CourseService courseService;

	public Map<String, Object> getDashboardStatistics() {
		Map<String, Object> statistics = new HashMap<>();

		long totalStudents = studentRepository.count();
		long totalTrainers = trainerRepository.count();
		long totalCourses = courseService.getAllCourses().size();
		long totalEnrollments = enrollementRepository.count();
		long completedCourses = courseProgressRepository.findByCompletedTrue().size();

		statistics.put("totalStudents", totalStudents);
		statistics.put("totalTrainers", totalTrainers);
		statistics.put("totalCourses", totalCourses);
		statistics.put("totalEnrollments", totalEnrollments);
		statistics.put("completedCourses", completedCourses);

		return statistics;
	}

	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	public List<Trainer> getAllTrainers() {
		return trainerRepository.findAll();
	}

	public List<Course> getAllCourses() {
		return courseService.getAllCourses();
	}

	public List<Enrollement> getAllEnrollments() {
		return enrollementRepository.findAll();
	}

	public void approveUser(int userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
		user.setApproved(true);
		userRepository.save(user);
	}
}
