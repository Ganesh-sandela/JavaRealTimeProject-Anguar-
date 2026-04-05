package com.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Coustmer;

public interface CoustmerRepo  extends JpaRepository<Coustmer, Integer> {

	
	public Coustmer findByEmail(String email);
}
