package com.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.AddressEntity;

public interface AddressEntityRepo extends JpaRepository<AddressEntity, Long> {

}
