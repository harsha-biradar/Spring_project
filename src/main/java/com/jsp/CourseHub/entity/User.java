package com.jsp.CourseHub.entity;


import com.jsp.CourseHub.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message="Name Should not be empty")
	@Size(min = 3, max = 20,message = "Name must contain 3 to 20 characters")
	private String name;
	@NotBlank(message="Email Should Not be blank")
	@Email(message="Invalid Email Format")
	private String email;
	@Pattern(regexp ="^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$")
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;

    private String otp;

    private boolean verified=false;

    private boolean approved=false;

	public void setEmail(String email) {
		this.email = email != null ? email.trim().toLowerCase() : null;
	}
}
