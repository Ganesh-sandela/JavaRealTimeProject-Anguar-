package com.repo;

import org.springframework.data.jpa.repository.JpaRepository;



public interface Customer  extends JpaRepository<com.entity.Customer, Integer> {

	
	public Customer findByEmail(String email);
}
