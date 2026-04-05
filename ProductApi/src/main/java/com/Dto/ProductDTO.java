package com.Dto;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDTO {

	
	private Long id;
	private String name;

	private String description;

	private String title;

	private BigDecimal unitPrice;

	private String imageUrl;

	private boolean active;

	private int unitsInStock;

	private Date dateCreated;

	private Date lastUpdate;
}
