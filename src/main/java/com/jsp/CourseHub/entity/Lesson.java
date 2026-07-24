package com.jsp.CourseHub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Lesson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String title;
	private String contentType; // VIDEO, TEXT, PDF, QUIZ, etc.
	private String contentUrl; // URL to video, PDF, etc.
	@org.hibernate.annotations.JdbcTypeCode(org.hibernate.type.SqlTypes.LONGVARCHAR)
	@jakarta.persistence.Column(columnDefinition = "TEXT")
	private String textContent; // For text-based lessons
	private int orderIndex; // To maintain the order of lessons in a module
	private int durationMinutes; // Estimated duration for video lessons

	@ManyToOne
	@JsonIgnore
	private Module module;
}
