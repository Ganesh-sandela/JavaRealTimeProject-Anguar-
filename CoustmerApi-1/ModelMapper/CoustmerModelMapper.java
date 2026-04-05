package com.ModelMapper;

import org.modelmapper.ModelMapper;

import com.dto.CoustmerDTO;
import com.entity.Coustmer;



public class CoustmerModelMapper {
public static final ModelMapper mapper=new ModelMapper();

public static CoustmerDTO cvrtEntityToDto(Coustmer Entity) {
	return mapper.map(Entity, CoustmerDTO.class);
}

public static Coustmer cvrDtoToEntity(CoustmerDTO dto) {
	return mapper.map(dto, Coustmer.class);
}
	
	
}
