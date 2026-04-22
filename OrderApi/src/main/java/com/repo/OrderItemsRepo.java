package com.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.OrderItems;

public interface OrderItemsRepo extends JpaRepository<OrderItems, Long> {

	public List<OrderItems> findByOrderId(Long id);
}
