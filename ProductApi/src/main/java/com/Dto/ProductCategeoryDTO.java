package com.Dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductCategeoryDTO {
	private Long id;

	private String categoryName;
	
	private List<ProductDTO> products;
}
