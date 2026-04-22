package com.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Address;


public interface AddressEntityRepo extends JpaRepository<Address, Long> {

}
