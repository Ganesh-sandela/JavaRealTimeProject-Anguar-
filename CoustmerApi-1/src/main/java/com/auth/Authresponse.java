package com.auth;

import com.dto.CoustmerDTO;

import lombok.Getter;
import lombok.Setter;
@Setter@Getter
public class Authresponse {

	private CoustmerDTO coustmerdto;
	
	private String token;
}
