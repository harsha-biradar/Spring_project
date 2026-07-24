package com.jsp.CourseHub.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateResponseDto {
    
    private int id;
    private StudentResponseDto student;
    private CourseResponseDto course;
    private String certificateNumber;
    private LocalDate issueDate;
    private String certificateUrl;
}
