package com.jsp.CourseHub.exception;

public class CourseNotFoundException extends RuntimeException {
	public CourseNotFoundException(String message) {
		super(message);
	}
}
