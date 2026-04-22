package com.DTO;

import com.entity.Order;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class OrderItemsDto {

	private Long id;
    private int quantity;
    private double unitPrice;
    private String imageUrl;
    private String name;
    
  
}
