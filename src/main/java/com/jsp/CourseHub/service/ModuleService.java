package com.jsp.CourseHub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.CourseHub.entity.Course;
import com.jsp.CourseHub.entity.Module;
import com.jsp.CourseHub.exception.CourseNotFoundException;
import com.jsp.CourseHub.repository.CourseRepository;
import com.jsp.CourseHub.repository.ModuleRepository;

@Service
public class ModuleService {

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	CourseRepository courseRepository;

	public Module saveModule(Module module, int courseId) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new CourseNotFoundException("Course Not Found"));
		module.setCourse(course);
		return moduleRepository.save(module);
	}

	public List<Module> getModulesByCourseId(int courseId) {
		return moduleRepository.findByCourseIdOrderByOrderIndexAsc(courseId);
	}

	public Module getModuleById(int id) {
		return moduleRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Module Not Found"));
	}

	public Module updateModule(int id, Module moduleDetails) {
		Module module = getModuleById(id);
		module.setTitle(moduleDetails.getTitle());
		module.setDescription(moduleDetails.getDescription());
		module.setOrderIndex(moduleDetails.getOrderIndex());
		return moduleRepository.save(module);
	}

	@Autowired
	com.jsp.CourseHub.repository.ModuleProgressRepository moduleProgressRepository;

	public void deleteModule(int id) {
		Module module = getModuleById(id);
		// ModuleProgress isn't a JPA-mapped collection on Module, so it won't be
		// cascade-deleted automatically; clean it up explicitly first to avoid a
		// foreign-key constraint violation.
		moduleProgressRepository.deleteAll(moduleProgressRepository.findByModuleId(id));
		moduleRepository.delete(module);
	}

	public int getTotalModulesByCourseId(int courseId) {
		return moduleRepository.countByCourseId(courseId);
	}
}
