package com.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.entity.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Setter
@Getter
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String orderTrackingno;

	private String razorPayOrderId;

	private String email;

	private String orderStatus;

	private double totalPrice;

	private int totalQuantity;

	private String razorPayPaymentId;

	private String invoiceurl;
	
	private String paymentStatus;

	@Column(name = "deliverydate")
	private LocalDate deliverydate;
     @CreationTimestamp
	private LocalDate dateCreated;
    @UpdateTimestamp
	private LocalDate lastUpdated;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;

}
