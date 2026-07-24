package com.jsp.CourseHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDto {
    
    private int id;
    private String name;
    private String email;
    private String collegeName;
    private String qualification;
    private boolean verified;
}
