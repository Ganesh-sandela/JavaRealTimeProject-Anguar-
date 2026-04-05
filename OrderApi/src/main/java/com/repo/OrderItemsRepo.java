package com.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.OrderItems;

public interface OrderItemsRepo extends JpaRepository<OrderItems, Long> {

}
