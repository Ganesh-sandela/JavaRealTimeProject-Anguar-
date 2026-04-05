package com.Mapper;

import org.modelmapper.ModelMapper;

import com.Dto.ProductDTO;
import com.entity.Products;

public class ProductMapper {

	public static final ModelMapper mapper=new ModelMapper();
	
	public static ProductDTO cvrtEntityToDto(Products Entity) {
		return mapper.map(Entity, ProductDTO.class);
	}
	
	public static Products cvrDtoToEntity(ProductDTO dto) {
		return mapper.map(dto, Products.class);
	}
}
