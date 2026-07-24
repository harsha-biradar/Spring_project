package com.jsp.CourseHub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.CourseHub.entity.Student;
import com.jsp.CourseHub.exception.StudentNotFoundException;
import com.jsp.CourseHub.repository.StudentRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.jsp.CourseHub.enums.Role;
import com.jsp.CourseHub.util.OtpUtil;

@Service
public class StudentService {
	@Autowired
	StudentRepository studentRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;

	public Student saveStudent(Student student) {
		String otp = OtpUtil.generateOtp();
		student.setOtp(otp);
		student.setVerified(false);
		student.setRole(Role.STUDENT);
		student.setPassword(passwordEncoder.encode(student.getPassword()));

		Student savedStudent = studentRepository.save(student);
		try {
			emailService.sendOtp(student.getEmail(), otp);
		} catch (Exception e) {
			// Don't let an email delivery failure fail the whole registration -
			// the account is already created; the user can request the OTP again.
			System.err.println("Failed to send OTP email to " + student.getEmail() + ": " + e.getMessage());
		}

		return savedStudent;
	}

	public List<Student> getAllStudents() {

		return studentRepository.findAll();
	}

	public Student getStudentById(int id) {

		return studentRepository.findById(id)
				.orElseThrow(() ->
				new StudentNotFoundException("Student Not Found"));
	}
}
