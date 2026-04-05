package com.service;

import org.springframework.stereotype.Service;

import com.entity.Order;

@Service
public interface RazorPayService {

	
	public com.razorpay.Order createRazorpayOrder(double Amount);
	
	 public String refundPayment(String paymentId, Double amount);
}
