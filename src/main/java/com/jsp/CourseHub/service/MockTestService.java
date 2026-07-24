package com.jsp.CourseHub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.CourseHub.entity.Course;
import com.jsp.CourseHub.entity.MockTest;
import com.jsp.CourseHub.entity.Question;
import com.jsp.CourseHub.exception.MockTestNotFoundException;
import com.jsp.CourseHub.repository.MockTestRepository;
import com.jsp.CourseHub.repository.QuestionRepository;

@Service
public class MockTestService {

	@Autowired
	private MockTestRepository mockTestRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private CourseService courseService;

	public MockTest saveMockTest(MockTest mockTest, int courseId) {
		Course course = courseService.getCourseById(courseId);
		mockTest.setCourse(course);
		if (mockTest.getQuestions() != null) {
			for (Question q : mockTest.getQuestions()) {
				q.setMockTest(mockTest);
			}
		}
		return mockTestRepository.save(mockTest);
	}

	public MockTest getMockTestById(int id) {
		return mockTestRepository.findById(id)
				.orElseThrow(() -> new MockTestNotFoundException("Mock Test Not Found with ID: " + id));
	}

	public List<MockTest> getMockTestsByCourseId(int courseId) {
		// Verify course exists
		courseService.getCourseById(courseId);
		return mockTestRepository.findByCourseId(courseId);
	}

	public Question addQuestionToMockTest(int mockTestId, Question question) {
		MockTest mockTest = getMockTestById(mockTestId);
		question.setMockTest(mockTest);
		return questionRepository.save(question);
	}

	public void deleteMockTest(int id) {
		MockTest mockTest = getMockTestById(id);
		mockTestRepository.delete(mockTest);
	}
}
