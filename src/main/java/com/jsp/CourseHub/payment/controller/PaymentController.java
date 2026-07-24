package com.jsp.CourseHub.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import com.jsp.CourseHub.response.ResponseStructure;
import com.jsp.CourseHub.entity.Student;
import com.jsp.CourseHub.entity.User;
import com.jsp.CourseHub.payment.service.PaymentService;
import com.jsp.CourseHub.repository.UserRepository;
import com.razorpay.Order;

import org.springframework.beans.factory.annotation.Value;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	    @Autowired
	    private PaymentService paymentService;

	    @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private com.jsp.CourseHub.repository.StudentRepository studentRepository;

	    @Value("${razorpay.key}")
	    private String razorpayKey;

	    @PostMapping("/create-order/{courseId}")
	    public ResponseEntity<ResponseStructure<Map<String, Object>>> createOrder(
	            @PathVariable int courseId,
	            Authentication authentication) throws Exception {

	        Student student = getLoggedInStudent(authentication);
	        Order order = paymentService.createOrder(student.getId(), courseId);

	        Map<String, Object> data = new HashMap<>();
	        data.put("orderId", order.get("id"));
	        data.put("amount", order.get("amount"));
	        data.put("currency", order.get("currency"));
	        data.put("key", razorpayKey);

	        ResponseStructure<Map<String, Object>> rs = new ResponseStructure<>();
	        rs.setStatus(HttpStatus.CREATED.value());
	        rs.setMessage("Order created successfully");
	        rs.setData(data);

	        return new ResponseEntity<>(rs, HttpStatus.CREATED);
	    }

	    @PostMapping("/verify")
	    public ResponseEntity<ResponseStructure<String>> verifyPayment(
	            @RequestParam String razorpayOrderId,
	            @RequestParam String razorpayPaymentId,
	            @RequestParam String razorpaySignature,
	            Authentication authentication) {

	        Student student = getLoggedInStudent(authentication);
	        boolean verified = paymentService.verifyPayment(razorpayOrderId, razorpayPaymentId, razorpaySignature, student.getId());

	        ResponseStructure<String> rs = new ResponseStructure<>();
	        if (verified) {
	            rs.setStatus(HttpStatus.OK.value());
	            rs.setMessage("Payment verified successfully, student enrolled.");
	            rs.setData("SUCCESS");
	            return new ResponseEntity<>(rs, HttpStatus.OK);
	        } else {
	            rs.setStatus(HttpStatus.BAD_REQUEST.value());
	            rs.setMessage("Payment verification failed.");
	            rs.setData("FAILURE");
	            return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
	        }
	    }

	    private Student getLoggedInStudent(Authentication authentication) {
	        User user = userRepository.findByEmail(authentication.getName());
	        if (user == null || user.getRole() != com.jsp.CourseHub.enums.Role.STUDENT) {
	            throw new IllegalStateException("Only students can make payments.");
	        }
	        return studentRepository.findById(user.getId())
	                .orElseThrow(() -> new IllegalStateException("Student record not found."));
	    }
}
