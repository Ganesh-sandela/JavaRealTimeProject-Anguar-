package com.responseApi;



import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseApi<T> {

	
	private Integer statuscode;
	private String msg;
	private T data;
}
