package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.DTO.OrderDTO;
import com.DTO.PaymentCallBackDTO;
import com.entity.Order;
import com.request.PurchaseOrderRequest;
import com.response.PurchaseOrderResponse;
@Service
public interface OrderService {

	
	public PurchaseOrderResponse createorder(PurchaseOrderRequest request);
	
	public List<OrderDTO> getOrderByEmail(String email);
	
	public PurchaseOrderResponse updateOrder(PaymentCallBackDTO paymentcallbackdto);
	
	
	public PurchaseOrderResponse cancelOrder(Long id);
}
