package com.jsp.CourseHub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.CourseHub.dto.ReviewRequestDto;
import com.jsp.CourseHub.dto.ReviewResponseDto;
import com.jsp.CourseHub.entity.Course;
import com.jsp.CourseHub.entity.Review;
import com.jsp.CourseHub.entity.Student;
import com.jsp.CourseHub.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	ReviewRepository reviewRepository;

	@Autowired
	StudentService studentService;

	@Autowired
	CourseService courseService;

	public ReviewResponseDto addReview(ReviewRequestDto requestDto) {
		Student student = studentService.getStudentById(requestDto.getStudentId());
		Course course = courseService.getCourseById(requestDto.getCourseId());

		Review review = new Review();
		review.setStudent(student);
		review.setCourse(course);
		review.setRating(requestDto.getRating());
		review.setComment(requestDto.getComment());

		Review savedReview = reviewRepository.save(review);
		return convertToResponseDto(savedReview);
	}

	public List<ReviewResponseDto> getCourseReviews(int courseId) {
		return reviewRepository.findByCourseId(courseId).stream()
				.map(this::convertToResponseDto)
				.toList();
	}

	public List<ReviewResponseDto> getStudentReviews(int studentId) {
		return reviewRepository.findByStudentId(studentId).stream()
				.map(this::convertToResponseDto)
				.toList();
	}

	public double getAverageRating(int courseId) {
		List<Review> reviews = reviewRepository.findByCourseId(courseId);
		if (reviews.isEmpty()) {
			return 0.0;
		}
		return reviews.stream()
				.mapToInt(Review::getRating)
				.average()
				.orElse(0.0);
	}

	private ReviewResponseDto convertToResponseDto(Review review) {
		ReviewResponseDto responseDto = new ReviewResponseDto();
		responseDto.setId(review.getId());
		responseDto.setRating(review.getRating());
		responseDto.setComment(review.getComment());
		responseDto.setCreatedAt(review.getCreatedAt());
		responseDto.setStudent(toStudentDto(review.getStudent()));
		responseDto.setCourse(toCourseDto(review.getCourse()));
		return responseDto;
	}

	private com.jsp.CourseHub.dto.StudentResponseDto toStudentDto(Student student) {
		if (student == null) {
			return null;
		}
		com.jsp.CourseHub.dto.StudentResponseDto dto = new com.jsp.CourseHub.dto.StudentResponseDto();
		dto.setId(student.getId());
		dto.setName(student.getName());
		dto.setEmail(student.getEmail());
		dto.setCollegeName(student.getCollegeName());
		dto.setQualification(student.getQualification());
		dto.setVerified(student.isVerified());
		return dto;
	}

	private com.jsp.CourseHub.dto.CourseResponseDto toCourseDto(Course course) {
		if (course == null) {
			return null;
		}
		com.jsp.CourseHub.dto.CourseResponseDto dto = new com.jsp.CourseHub.dto.CourseResponseDto();
		dto.setId(course.getId());
		dto.setTitle(course.getTitle());
		dto.setDiscription(course.getDiscription());
		dto.setPrice(course.getPrice());
		dto.setDuration(course.getDuration());
		dto.setCreatedDate(course.getCreatedDate());
		return dto;
	}
}
