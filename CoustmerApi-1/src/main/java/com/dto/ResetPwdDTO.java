package com.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPwdDTO {

	private String name;
	private String email;
	private String oldPwd;
	private String newPwd;
	private String conformPwd;
	private String resetPwd;
}
