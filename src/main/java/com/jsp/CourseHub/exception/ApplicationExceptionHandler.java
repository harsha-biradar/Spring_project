package com.jsp.CourseHub.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e){
		Map<String, String> errors=new HashMap<String, String>();
		e.getBindingResult().getFieldErrors().forEach(error->{
			errors.put(error.getField(),error.getDefaultMessage());
		});
	 return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<String> handleCategoryException(
			CategoryNotFoundException e) {

		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CourseNotFoundException.class)
	public ResponseEntity<String> handleCourseException(
			CourseNotFoundException e) {

		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(TrainerNotFoundException.class)
	public ResponseEntity<String> handleTrainerException(
			TrainerNotFoundException e) {

		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<String> handleStudentException(
			StudentNotFoundException e) {

		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MockTestNotFoundException.class)
	public ResponseEntity<String> handleMockTestException(
			MockTestNotFoundException e) {

		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<String> handleUnauthorizedException(
			UnauthorizedException e) {

		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, String>> handleRuntimeException(
			RuntimeException e) {

		Map<String, String> body = new HashMap<>();
		body.put("message", e.getMessage() != null ? e.getMessage() : "Something went wrong");
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
}

