package com.jsp.CourseHub.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {
    
    private int id;
    private String title;
    private String discription;
    private double price;
    private int duration;
    private LocalDate createdDate;
    private CourseResponseDto category;
    private UserResponseDto trainer;
}
