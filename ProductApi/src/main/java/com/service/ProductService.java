package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Dto.ProductCategeoryDTO;
import com.Dto.ProductDTO;
import com.entity.Products;
@Service
public interface ProductService {

	public List<ProductCategeoryDTO> findall();
	
	public List<ProductDTO> findByProductsCategoryId(Long CategoryId);
	
	public ProductDTO findByProductId(Long id);
	
	public List<ProductDTO> findByProductsName(String name);
	
}
