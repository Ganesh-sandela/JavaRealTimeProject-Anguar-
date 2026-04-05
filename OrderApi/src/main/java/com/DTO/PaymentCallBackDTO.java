package com.DTO;

import lombok.Data;

@Data
public class PaymentCallBackDTO {

	
	private String razorPayOrderId;
	private String razorPayPaymentId;
	private String razorpaySignature;
}
