package com.jsp.CourseHub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.CourseHub.entity.Course;
import com.jsp.CourseHub.entity.Enrollement;
import com.jsp.CourseHub.entity.Student;
import com.jsp.CourseHub.repository.EnrollementRepository;

@Service
public class EnrollementService {

	@Autowired
    EnrollementRepository  enrollmentRepository;

	@Autowired
	StudentService studentService;

	@Autowired
	CourseService courseService;

	public Enrollement enrollCourse(int studentId, int courseId) {

		Enrollement existingEnrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
				.orElse(null);
		if (existingEnrollment != null) {
			existingEnrollment.setStatus("ENROLLED");
			return enrollmentRepository.save(existingEnrollment);
		}

		Student student = studentService.getStudentById(studentId);

		Course course = courseService.getCourseById(courseId);

		Enrollement enrollment = new Enrollement();

		enrollment.setStudent(student);

		enrollment.setCourse(course);

		enrollment.setStatus("ENROLLED");

		return enrollmentRepository.save(enrollment);
	}

	public List<Enrollement> getAllEnrollments() {

		return enrollmentRepository.findAll();
	}
}
