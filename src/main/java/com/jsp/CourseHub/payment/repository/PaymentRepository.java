package com.jsp.CourseHub.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.CourseHub.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{
	Payment findByRazorpayOrderId(String razorpayOrderId);
}
