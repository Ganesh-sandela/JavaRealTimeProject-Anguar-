package com.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CoustmerDTO {

	private Integer id;
	private String name;
	private String email;
	private Long phno;
	private String conformPwd;
	private String newPwd;
	private String oldPwd;
	private String resetPwd;
}
