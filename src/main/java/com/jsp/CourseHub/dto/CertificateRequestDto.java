package com.jsp.CourseHub.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateRequestDto {
    
    @NotNull(message = "Student ID is required")
    private int studentId;
    
    @NotNull(message = "Course ID is required")
    private int courseId;
}
