package com.service;

import org.springframework.stereotype.Service;

import com.auth.Authresponse;
import com.dto.CoustmerDTO;
import com.dto.ResetPwdDTO;
import com.entity.Coustmer;

@Service
public interface CoustmerService {

	
	public Boolean register(CoustmerDTO dto);
	
	public Boolean isUniqueEmail(String email);
	
	public CoustmerDTO getCoustmerByEmail(String eamil);
	
	public Boolean resetPwd(ResetPwdDTO rdto);
	
	public Authresponse login(CoustmerDTO dto);
	
	public Boolean forgotPwd(String email);
}
