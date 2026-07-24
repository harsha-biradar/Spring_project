package com.jsp.CourseHub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.CourseHub.entity.Trainer;
import com.jsp.CourseHub.exception.TrainerNotFoundException;
import com.jsp.CourseHub.repository.TrainerRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.jsp.CourseHub.enums.Role;
import com.jsp.CourseHub.util.OtpUtil;

@Service
public class TrainerService {
	@Autowired
	TrainerRepository trainerRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;

	public Trainer saveTrainer(Trainer trainer) {
		String otp = OtpUtil.generateOtp();
		trainer.setOtp(otp);
		trainer.setVerified(false);
		trainer.setRole(Role.TRAINER);
		trainer.setPassword(passwordEncoder.encode(trainer.getPassword()));

		Trainer savedTrainer = trainerRepository.save(trainer);
		try {
			emailService.sendOtp(trainer.getEmail(), otp);
		} catch (Exception e) {
			// Don't let an email delivery failure fail the whole registration -
			// the account is already created; the user can request the OTP again.
			System.err.println("Failed to send OTP email to " + trainer.getEmail() + ": " + e.getMessage());
		}

		return savedTrainer;
	}

	public List<Trainer> getAllTrainers() {

		return trainerRepository.findAll();
	}

	public Trainer getTrainerById(int id) {

		return trainerRepository.findById(id)
				.orElseThrow(() ->
				new TrainerNotFoundException("Trainer Not Found"));
	}
}
