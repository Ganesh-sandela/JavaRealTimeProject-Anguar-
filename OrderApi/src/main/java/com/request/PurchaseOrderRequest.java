package com.request;

import java.util.List;

import com.DTO.AddressDTO;
import com.DTO.CustomerDTO;
import com.DTO.OrderDTO;
import com.DTO.OrderItemsDto;
import com.entity.Address;
import com.entity.Order;

import lombok.Data;

@Data
public class PurchaseOrderRequest {

	
	private CustomerDTO customer;
	
	private OrderDTO order;
	
	private AddressDTO address;
	
	private List<OrderItemsDto> orderItems;
	
}
