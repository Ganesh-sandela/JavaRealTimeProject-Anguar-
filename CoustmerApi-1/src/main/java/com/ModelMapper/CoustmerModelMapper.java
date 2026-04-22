package com.ModelMapper;

import org.modelmapper.ModelMapper;

import com.dto.CoustmerDTO;
import com.entity.Customer;



public class CoustmerModelMapper {
public static final ModelMapper mapper=new ModelMapper();

public static CoustmerDTO cvrtEntityToDto(Customer Entity) {
	return mapper.map(Entity, CoustmerDTO.class);
}

public static Customer cvrDtoToEntity(CoustmerDTO dto) {
	return mapper.map(dto, Customer.class);
}
	
	
}
