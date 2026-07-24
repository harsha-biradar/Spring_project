package com.jsp.CourseHub.payment.entity;

import java.time.LocalDateTime;

import com.jsp.CourseHub.entity.Course;
import com.jsp.CourseHub.entity.Student;
import com.jsp.CourseHub.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Payment {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    private String razorpayOrderId;

	    private String razorpayPaymentId;

	    private double amount;

	    @Enumerated(EnumType.STRING)
	    private PaymentStatus status;

	    private LocalDateTime paymentTime = LocalDateTime.now();

	    @ManyToOne
	    private Student student;

	    @ManyToOne
	    private Course course;
}
