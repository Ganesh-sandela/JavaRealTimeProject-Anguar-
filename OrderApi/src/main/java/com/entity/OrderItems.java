package com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Orderitemid;
    
    private String ProductName;
    
    private int quantity;
    
    private double unitprice;
    
    private String imageurl;
    
   
    
//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private Coustmer coustmer; 
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
	
}
