package com.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseOrderResponse {

	private String razorpayorderId;
	
	private String orderStatus;
	
	private String orderTrackingNumber;
}
