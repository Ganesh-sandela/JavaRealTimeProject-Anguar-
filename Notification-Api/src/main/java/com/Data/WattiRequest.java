package com.Data;

import java.util.List;

import lombok.Data;

@Data
public class WattiRequest {

	private String template;
	
	private String broadcast;
	
	private List<WattiParameter> param;
}
