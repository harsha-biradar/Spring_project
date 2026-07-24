package com.jsp.CourseHub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDto {
    
    @NotBlank(message = "Name should not be empty")
    @Size(min = 3, max = 20, message = "Name must contain 3 to 20 characters")
    private String name;
    
    @NotBlank(message = "Email should not be blank")
    @Email(message = "Invalid Email Format")
    private String email;
    
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$", 
             message = "Password must contain at least one uppercase, one lowercase, one digit, one special character and minimum 8 characters")
    private String password;
    
    @NotBlank(message = "College name should not be empty")
    private String collegeName;
    
    @NotBlank(message = "Qualification should not be empty")
    private String qualification;
    
    private String otp;
}
