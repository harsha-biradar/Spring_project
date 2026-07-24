package com.jsp.CourseHub.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDto {
    
    @NotBlank(message = "Course title should not be empty")
    private String title;
    
    private String discription;
    
    @Min(value = 1, message = "Price must be greater than zero")
    private double price;
    
    @Min(value = 1, message = "Duration must be greater than zero")
    private int duration;
    
    private int categoryId;
    
    private int trainerId;
}
