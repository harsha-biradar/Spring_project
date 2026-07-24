package com.jsp.CourseHub.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.CourseHub.dto.CourseProgressRequestDto;
import com.jsp.CourseHub.dto.CourseProgressResponseDto;
import com.jsp.CourseHub.entity.Course;
import com.jsp.CourseHub.entity.CourseProgress;
import com.jsp.CourseHub.entity.Student;
import com.jsp.CourseHub.repository.CourseProgressRepository;

@Service
public class CourseProgressService {

	@Autowired
	CourseProgressRepository courseProgressRepository;

	@Autowired
	StudentService studentService;

	@Autowired
	CourseService courseService;

	public CourseProgressResponseDto updateProgress(CourseProgressRequestDto requestDto) {
		Student student = studentService.getStudentById(requestDto.getStudentId());
		Course course = courseService.getCourseById(requestDto.getCourseId());

		Optional<CourseProgress> existingProgress = courseProgressRepository
				.findByStudentIdAndCourseId(requestDto.getStudentId(), requestDto.getCourseId());

		CourseProgress progress;
		if (existingProgress.isPresent()) {
			progress = existingProgress.get();
		} else {
			progress = new CourseProgress();
			progress.setStudent(student);
			progress.setCourse(course);
		}

		progress.setProgressPercentage(requestDto.getProgressPercentage());
		progress.setLastUpdated(LocalDateTime.now());

		if (requestDto.getProgressPercentage() >= 100 && !progress.isCompleted()) {
			progress.setCompleted(true);
			progress.setCompletionDate(LocalDateTime.now());
		}

		CourseProgress savedProgress = courseProgressRepository.save(progress);
		return convertToResponseDto(savedProgress);
	}

	public CourseProgressResponseDto getProgress(int studentId, int courseId) {
		CourseProgress progress = courseProgressRepository.findByStudentIdAndCourseId(studentId, courseId)
				.orElseThrow(() -> new RuntimeException("Progress not found"));
		return convertToResponseDto(progress);
	}

	public List<CourseProgressResponseDto> getStudentProgress(int studentId) {
		return courseProgressRepository.findByStudentId(studentId).stream()
				.map(this::convertToResponseDto)
				.toList();
	}

	public List<CourseProgressResponseDto> getCourseProgress(int courseId) {
		return courseProgressRepository.findByCourseId(courseId).stream()
				.map(this::convertToResponseDto)
				.toList();
	}

	public List<CourseProgressResponseDto> getAllCompletedProgress() {
		return courseProgressRepository.findByCompletedTrue().stream()
				.map(this::convertToResponseDto)
				.toList();
	}

	private CourseProgressResponseDto convertToResponseDto(CourseProgress progress) {
		CourseProgressResponseDto responseDto = new CourseProgressResponseDto();
		responseDto.setId(progress.getId());
		responseDto.setProgressPercentage(progress.getProgressPercentage());
		responseDto.setLastUpdated(progress.getLastUpdated());
		responseDto.setCompleted(progress.isCompleted());
		responseDto.setCompletionDate(progress.getCompletionDate());
		responseDto.setStudent(toStudentDto(progress.getStudent()));
		responseDto.setCourse(toCourseDto(progress.getCourse()));
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
