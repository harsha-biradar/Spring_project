package com.jsp.CourseHub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.jsp.CourseHub.entity.User;
import com.jsp.CourseHub.repository.UserRepository;
import com.jsp.CourseHub.util.OtpUtil;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User saveUser(User user) {
		 String otp = OtpUtil.generateOtp();

	        user.setOtp(otp);
	        user.setVerified(false);
	        user.setPassword(passwordEncoder.encode(user.getPassword()));

	        User savedUser = userRepository.save(user);

	        emailService.sendOtp(user.getEmail(), otp);

	        return savedUser;
	}
	
	public String verifyOtp(int userId, String otp) {

	    User user = userRepository.findById(userId)
	    		.orElseThrow(() -> new IllegalArgumentException("Invalid user id"));

	    if(user.getOtp() != null && user.getOtp().equals(otp)) {

	        user.setVerified(true);
	        user.setOtp(null);

	        userRepository.save(user);

	        return "Email Verified Successfully";
	    }

	    return "Invalid OTP";
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}

	public String resendOtp(int userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
		
		String otp = OtpUtil.generateOtp();
		user.setOtp(otp);
		userRepository.save(user);
		
		emailService.sendOtp(user.getEmail(), otp);
		return "OTP Resent Successfully";
	}

	public String requestForgotPassword(String email) {
		User user = userRepository.findByEmail(email != null ? email.trim().toLowerCase() : null);
		if (user == null) {
			throw new IllegalArgumentException("User with this email does not exist");
		}
		
		String otp = OtpUtil.generateOtp();
		user.setOtp(otp);
		userRepository.save(user);
		
		emailService.sendOtp(user.getEmail(), otp);
		return "Password reset OTP sent successfully";
	}

	public String resetPassword(String email, String otp, String newPassword) {
		User user = userRepository.findByEmail(email != null ? email.trim().toLowerCase() : null);
		if (user == null) {
			throw new IllegalArgumentException("User with this email does not exist");
		}
		if (user.getOtp() == null || !user.getOtp().equals(otp)) {
			throw new IllegalArgumentException("Invalid OTP");
		}
		
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setOtp(null);
		userRepository.save(user);
		return "Password reset successfully";
	}
}
