package com.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

	


	public Order findByRazorPayOrderId(String razorPayOrderId);

	public List<Order> findByEmail(String email);
}
