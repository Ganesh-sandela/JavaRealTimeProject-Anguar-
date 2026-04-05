package com.Data;

import java.util.List;

import lombok.Data;

@Data
public class WattiResponse {

	private String name;
	private String phno;
	private String validWhatsappNo;
	private List<WattiParameter> wattiParam;
	
}
