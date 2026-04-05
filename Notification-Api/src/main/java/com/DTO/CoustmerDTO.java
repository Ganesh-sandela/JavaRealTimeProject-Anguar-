package com.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
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
