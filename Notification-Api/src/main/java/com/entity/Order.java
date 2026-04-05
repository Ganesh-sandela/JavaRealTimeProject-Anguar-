package com.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
	private Long orderid;

	private String orderTrackingno;

	private String razorPayOrderId;

	private String email;

	private String orderStatus;

	private double totalPrice;

	private int totalquantity;

	private String razorPayPaymentId;

	private String invoiceUrl;
	
	private String paymentStatus;

	@Column(name = "deliveryDate")
	private LocalDate deliveryDate;
     @CreationTimestamp
	private LocalDate dateCreated;
    @UpdateTimestamp
	private LocalDate lastUpdated;

	@ManyToOne
	@JoinColumn(name = "coustmer_id")
	private Coustmer coustmer;

	@ManyToOne
	@JoinColumn(name = "addr_id")
	private AddressEntity address;

}
