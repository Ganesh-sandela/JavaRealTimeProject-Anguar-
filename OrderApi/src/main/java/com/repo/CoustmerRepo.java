package com.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DTO.EmailRequest;
import com.entity.Customer;

public interface CoustmerRepo  extends JpaRepository<Customer, Integer> {

	
	public Customer findByEmail(String email);

//	public Customer findByEmail(EmailRequest email);
}
