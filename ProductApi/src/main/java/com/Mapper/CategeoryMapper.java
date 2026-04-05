package com.Mapper;

import org.modelmapper.ModelMapper;

import com.Dto.ProductCategeoryDTO;
import com.Dto.ProductDTO;
import com.entity.ProductCategory;
import com.entity.Products;

public class CategeoryMapper {

public static final ModelMapper mapper=new ModelMapper();
	
	public static ProductCategeoryDTO cvrtEntityToDto(ProductCategory Entity) {
		return mapper.map(Entity, ProductCategeoryDTO.class);
	}
	
	public static ProductCategory cvrDtoToEntity(ProductCategeoryDTO dto) {
		return mapper.map(dto, ProductCategory.class);
	}
}
