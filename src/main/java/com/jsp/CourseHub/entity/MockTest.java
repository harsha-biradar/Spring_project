package com.jsp.CourseHub.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MockTest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "Mock test title should not be empty")
	private String title;

	@Min(value = 1, message = "Duration must be at least 1 minute")
	private int duration; // in minutes

	@ManyToOne
	private Course course;

	@OneToMany(mappedBy = "mockTest", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Question> questions;

	@OneToMany(mappedBy = "mockTest", cascade = CascadeType.ALL, orphanRemoval = true)
	@com.fasterxml.jackson.annotation.JsonIgnore
	private List<MockTestAttempt> attempts;

}
