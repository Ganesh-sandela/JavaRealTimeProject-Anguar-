package com.controller;

import java.util.List;

import org.hibernate.annotations.ConcreteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.ls.LSInput;

import com.DTO.OrderDTO;
import com.DTO.PaymentCallBackDTO;
import com.request.PurchaseOrderRequest;
import com.response.PurchaseOrderResponse;
import com.responseApi.ResponseApi;
import com.service.OrderServiceImpl;

@RestController
public class OrderRestController {
     
	
	@Autowired
	private OrderServiceImpl ordserv;
	
	@PostMapping("/order")
	public ResponseEntity<ResponseApi<PurchaseOrderResponse>>   createOrder(@RequestBody PurchaseOrderRequest request){
		ResponseApi<PurchaseOrderResponse> response = new ResponseApi<>();
		PurchaseOrderResponse orderResp = ordserv.createorder(request);
		
		if (orderResp!=null) {
			response.setStatuscode(200);
			response.setMsg("order created");
			response.setData(orderResp);
			return new ResponseEntity<>(response,HttpStatus.CREATED);
		}
		else {
			response.setStatuscode(500);
			response.setData(null);
			response.setMsg("failed to create order");
			return new  ResponseEntity<ResponseApi<PurchaseOrderResponse>>(response ,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/order")
	public ResponseEntity<ResponseApi<PurchaseOrderResponse>>   updateOrder(@RequestBody PaymentCallBackDTO callbackdto){
		ResponseApi<PurchaseOrderResponse> response = new ResponseApi<>();
		PurchaseOrderResponse orderResp = ordserv.updateOrder(callbackdto);
		
		if (orderResp!=null) {
			response.setStatuscode(200);
			response.setMsg("order updated");
			response.setData(orderResp);
			return new ResponseEntity<>(response,HttpStatus.CREATED);
		}
		else {
			response.setStatuscode(500);
			response.setData(null);
			response.setMsg("failed to Update order");
			return new  ResponseEntity<ResponseApi<PurchaseOrderResponse>>(response ,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/order/{email}")
	public ResponseEntity<ResponseApi<List<OrderDTO>>>   getOrders(@PathVariable String email){
		ResponseApi<List<OrderDTO>> response = new ResponseApi<>();
              List<OrderDTO> orderByEmail = ordserv.getOrderByEmail(email);
		
		if (!orderByEmail.isEmpty()) {
			response.setStatuscode(200);
			response.setMsg("order created");
			response.setData(orderByEmail);
			return new ResponseEntity<>(response,HttpStatus.CREATED);
		}
		else {
			response.setStatuscode(500);
			response.setData(null);
			response.setMsg("failed to create order");
			return new  ResponseEntity<>(response ,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/order/{id}")
	public ResponseEntity<ResponseApi<PurchaseOrderResponse>> cancelOrder(@PathVariable Long id){
		
	ResponseApi<PurchaseOrderResponse>	response=new ResponseApi<>();
	PurchaseOrderResponse cancelOrder = ordserv.cancelOrder(id);
	     if (cancelOrder!=null) {
	    	 response.setStatuscode(200);
				response.setMsg("order Cancelled");
				response.setData(cancelOrder);
				return new ResponseEntity<>(response,HttpStatus.CREATED);
			}
			else {
				response.setStatuscode(404);
				response.setData(null);
				response.setMsg("failed to create order");
				return new  ResponseEntity<>(response ,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
}
