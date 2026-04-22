package com.response;

import java.util.List;

import com.entity.Address;
import com.entity.Customer;
import com.entity.Order;
import com.entity.OrderItems;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdressResponse {

	private Customer customer;
	
	private List<Address> address;
	
	private  List<Order> order;
	
	private List<OrderItems> orderItems;
}
