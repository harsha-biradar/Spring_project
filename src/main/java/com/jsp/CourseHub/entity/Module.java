package com.jsp.CourseHub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Module {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String title;
	private String description;
	private int orderIndex; // To maintain the order of modules in a course

	@ManyToOne
	private Course course;

	@OneToMany(mappedBy = "module", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
	@OrderBy("orderIndex ASC")
	private List<Lesson> lessons = new ArrayList<>();
}
