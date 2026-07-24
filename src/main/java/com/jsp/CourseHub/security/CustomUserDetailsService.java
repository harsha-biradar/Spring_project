package com.jsp.CourseHub.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jsp.CourseHub.entity.User;
import com.jsp.CourseHub.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email != null ? email.trim().toLowerCase() : null);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}

		// Prevent login if account email is not verified
		if (!user.isVerified()) {
			throw new DisabledException("User email verification is pending. Please verify using OTP first.");
		}
		
		// Prevent login if Student or Trainer is not approved by Admin
		if ((user.getRole() == com.jsp.CourseHub.enums.Role.STUDENT || user.getRole() == com.jsp.CourseHub.enums.Role.TRAINER) && !user.isApproved()) {
			throw new DisabledException("Your account is pending admin approval.");
		}

		if (user.getRole() == null) {
			throw new DisabledException("User role is not assigned. Please contact admin.");
		}

		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getEmail())
				.password(user.getPassword())
				.roles(user.getRole().name())
				.build();
	}
}
