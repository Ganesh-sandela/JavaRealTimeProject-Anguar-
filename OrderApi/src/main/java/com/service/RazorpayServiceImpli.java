package com.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;

import lombok.SneakyThrows;


@Service
public class RazorpayServiceImpli implements RazorPayService{

	@Value("${razorpay.key.id}")
	private String razorpaykey;
	
	@Value("${razorpay.key.secret}")
	private String razorpaysecret;
	
	
	private RazorpayClient client;
	
	@Override
	@SneakyThrows
	public com.razorpay.Order createRazorpayOrder(double Amount) {
		
		this.client= new RazorpayClient(razorpaykey,razorpaysecret);
		JSONObject orderRequest=new JSONObject();
		orderRequest.put("amount", Amount * 100); //amount in pisa
		orderRequest.put("currency", "INR");
		orderRequest.put("payment_capture", 1);
	  return client.orders.create(orderRequest);
	
	}

	@Override
	@SneakyThrows
	public String refundPayment(String paymentId, Double amount) {
		this.client= new RazorpayClient(razorpaykey,razorpaysecret);
		JSONObject refundreq=new JSONObject();
		refundreq.put("payment_id",paymentId);
		refundreq.put("amount", amount);
		
		Refund refund = client.payments.refund(refundreq);
		return refund.get("id");
	}
	
}