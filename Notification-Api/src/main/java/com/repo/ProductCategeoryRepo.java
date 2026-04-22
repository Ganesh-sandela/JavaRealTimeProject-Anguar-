package com.repo;



import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.ProductCategory;

public interface ProductCategeoryRepo extends JpaRepository<ProductCategory, Long> {

}
