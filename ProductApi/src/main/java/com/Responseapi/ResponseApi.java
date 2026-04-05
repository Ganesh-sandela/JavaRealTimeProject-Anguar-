package com.Responseapi;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseApi<T> {
  
	private Integer status;
	
	private String msg;
	
	private T data;
}
