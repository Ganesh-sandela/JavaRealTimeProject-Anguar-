package com.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

	


	public Order findByRazorPayOrderId(String razorPayOrderId);

	public List<Order> findByEmail(String email);
	
	List<Order> findByDeliverydate(LocalDate deliverydate);

	List<Order> findByDeliverydateAfter(LocalDate date);
	
	public List<Order> findByOrderStatus(String status);
}
