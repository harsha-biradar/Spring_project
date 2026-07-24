package com.jsp.CourseHub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.CourseHub.entity.Lesson;
import com.jsp.CourseHub.entity.Module;
import com.jsp.CourseHub.repository.LessonRepository;
import com.jsp.CourseHub.repository.ModuleRepository;

@Service
public class LessonService {

	@Autowired
	LessonRepository lessonRepository;

	@Autowired
	ModuleRepository moduleRepository;

	public Lesson saveLesson(Lesson lesson, int moduleId) {
		Module module = moduleRepository.findById(moduleId)
				.orElseThrow(() -> new RuntimeException("Module Not Found"));
		lesson.setModule(module);
		return lessonRepository.save(lesson);
	}

	public List<Lesson> getLessonsByModuleId(int moduleId) {
		return lessonRepository.findByModuleIdOrderByOrderIndexAsc(moduleId);
	}

	public Lesson getLessonById(int id) {
		return lessonRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Lesson Not Found"));
	}

	public Lesson updateLesson(int id, Lesson lessonDetails) {
		Lesson lesson = getLessonById(id);
		lesson.setTitle(lessonDetails.getTitle());
		lesson.setContentType(lessonDetails.getContentType());
		lesson.setContentUrl(lessonDetails.getContentUrl());
		lesson.setTextContent(lessonDetails.getTextContent());
		lesson.setOrderIndex(lessonDetails.getOrderIndex());
		lesson.setDurationMinutes(lessonDetails.getDurationMinutes());
		return lessonRepository.save(lesson);
	}

	public void deleteLesson(int id) {
		Lesson lesson = getLessonById(id);
		lessonRepository.delete(lesson);
	}

	public int getTotalLessonsByModuleId(int moduleId) {
		return lessonRepository.findByModuleId(moduleId).size();
	}
}
