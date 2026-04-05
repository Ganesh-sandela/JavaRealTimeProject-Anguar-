package com.request;

import java.util.List;

import com.DTO.AddressDTO;
import com.DTO.CoustmerDTO;
import com.DTO.OrderDTO;
import com.DTO.OrderItemsDto;
import com.entity.AddressEntity;
import com.entity.Coustmer;
import com.entity.Order;

import lombok.Data;

@Data
public class PurchaseOrderRequest {

	
	private CoustmerDTO coustmer;
	
	private OrderDTO order;
	
	private AddressDTO addressEntity;
	
	private List<OrderItemsDto> orderItems;
	
}
