package com.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Address;


public interface AddressEntityRepo extends JpaRepository<Address, Long> {

	public  List<Address> findByCustomerId(Integer integer);

}
