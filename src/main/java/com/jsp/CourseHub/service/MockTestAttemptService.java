package com.jsp.CourseHub.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.CourseHub.entity.Enrollement;
import com.jsp.CourseHub.entity.MockTest;
import com.jsp.CourseHub.entity.MockTestAttempt;
import com.jsp.CourseHub.entity.Question;
import com.jsp.CourseHub.entity.Student;
import com.jsp.CourseHub.exception.UnauthorizedException;
import com.jsp.CourseHub.repository.EnrollementRepository;
import com.jsp.CourseHub.repository.MockTestAttemptRepository;

@Service
public class MockTestAttemptService {

	@Autowired
	private MockTestAttemptRepository mockTestAttemptRepository;

	@Autowired
	private MockTestService mockTestService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private EnrollementRepository enrollmentRepository;

	public MockTestAttempt submitAttempt(int studentId, int mockTestId, Map<Integer, String> answers) {
		Student student = studentService.getStudentById(studentId);
		MockTest mockTest = mockTestService.getMockTestById(mockTestId);

		// Verify enrollment in the mock test's course
		Optional<Enrollement> enrollment = enrollmentRepository.findByStudentIdAndCourseId(
				studentId, mockTest.getCourse().getId());

		if (enrollment.isEmpty() || !"ENROLLED".equalsIgnoreCase(enrollment.get().getStatus())) {
			throw new UnauthorizedException("You must purchase the course '" + mockTest.getCourse().getTitle() 
					+ "' before attempting this mock test.");
		}

		// Calculate score
		int score = 0;
		List<Question> questions = mockTest.getQuestions();
		for (Question question : questions) {
			String studentAnswer = answers.get(question.getId());
			if (studentAnswer != null && studentAnswer.trim().equalsIgnoreCase(question.getCorrectOption().trim())) {
				score++;
			}
		}

		MockTestAttempt attempt = new MockTestAttempt();
		attempt.setStudent(student);
		attempt.setMockTest(mockTest);
		attempt.setScore(score);
		attempt.setTotalQuestions(questions.size());
		attempt.setAttemptTime(LocalDateTime.now());

		return mockTestAttemptRepository.save(attempt);
	}

	public List<MockTestAttempt> getAttemptsByStudentId(int studentId) {
		// Verify student exists
		studentService.getStudentById(studentId);
		return mockTestAttemptRepository.findByStudentId(studentId);
	}
}
