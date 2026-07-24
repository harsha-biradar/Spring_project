package com.jsp.CourseHub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailsender;
	
	public void sendOtp(String toEmail,String otp) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("akankshadesai932@gmail.com");
		message.setTo(toEmail);
		message.setSubject("CourseHub Email Verification OTP");
		message.setText("Your CourseHub email verification OTP is: " + otp + "\n\nThis code is required before you can log in.");
		mailsender.send(message);
	}

}
