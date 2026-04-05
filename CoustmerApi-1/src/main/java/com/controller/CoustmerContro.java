package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.Authresponse;
import com.dto.CoustmerDTO;
import com.dto.ResetPwdDTO;
import com.responseApi.ResponseApi;
import com.service.CoustmerServiceImpl;

@RestController
public class CoustmerContro {

	

	@Autowired
	private CoustmerServiceImpl cserv;

	@Autowired
	private BCryptPasswordEncoder Pwdencode;


	@PostMapping("/register")
	public ResponseEntity<ResponseApi<String>> register(@RequestBody CoustmerDTO dto) {

		ResponseApi<String> Response = new ResponseApi<>();
		Boolean uniqueEmail = cserv.isUniqueEmail(dto.getEmail());
		if (!uniqueEmail) {
			Response.setStatuscode(400);
			Response.setMsg("failed");
			Response.setData("Duplicate Email");
			return new ResponseEntity<>(Response, HttpStatus.BAD_REQUEST);
		}

		Boolean register = cserv.register(dto);
		if (register) {
			Response.setStatuscode(200);
			Response.setMsg("succesfully registration completed...");
			Response.setData("registration succes");
		} else {
			Response.setStatuscode(500);
			Response.setMsg("  failed...");
			Response.setData("registration Failed");
		}
		return new ResponseEntity<ResponseApi<String>>(Response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/resetpwd")
	public ResponseEntity<ResponseApi<String>> resetPwd(@RequestBody ResetPwdDTO resetPwdDto) {
		ResponseApi<String> response = new ResponseApi<>();
		CoustmerDTO c = cserv.getCoustmerByEmail(resetPwdDto.getEmail());

		if (!Pwdencode.matches(resetPwdDto.getOldPwd(), c.getOldPwd())) {
			response.setStatuscode(400);
			response.setMsg("Failed");
			response.setData("old pwd is incorrect");
			return new ResponseEntity<ResponseApi<String>>(response, HttpStatus.BAD_REQUEST);

		}
//	        resetPwdDto.getNewPwd().equals(resetPwdDto.getConformPwd());
//	        if (resetPwdDto.getNewPwd().equals(resetPwdDto.getConformPwd())) {
//				String oldPwd = resetPwdDto.getOldPwd();
//				String dtoPwd = Pwdencode.encode(oldPwd);
//				String entityPwd = c.getOldPwd();
//				if (dtoPwd.equals(entityPwd)) {
//					cserv.resetPwd(resetPwdDto);
//				}

		boolean isResetCompleted = cserv.resetPwd(resetPwdDto);
		if (isResetCompleted) {
			response.setStatuscode(200);
			response.setMsg("Pwd Updated");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatuscode(500);
			response.setMsg("Reset Pwd Failed");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseApi<Authresponse>> login(@RequestBody CoustmerDTO dto) {

		ResponseApi<Authresponse> response = new ResponseApi<>();

		Authresponse login = cserv.login(dto);

		if (login != null) {
			response.setStatuscode(200);
			response.setMsg("Login Success");
			response.setData(login);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatuscode(400);
			response.setMsg("Invalid Credentials...");
			response.setData(null);

			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/forgotpwd")
	public ResponseEntity<ResponseApi<String>> forgotPwd(@RequestParam String email) {
		ResponseApi<String> response = new ResponseApi<>();
		Boolean status = cserv.forgotPwd(email);
		if (status) {
			response.setStatuscode(200);
			response.setData("email sent  reset Pwd");
			response.setMsg("success");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatuscode(400);
			response.setData("No Account Found");
			response.setMsg("failed");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

	}

//	@PostMapping("/resetPwd")
//	public boolean resetpwd(@RequestBody ResetPwdDTO dto) {
//		CoustmerDTO c = cserv.getCoustmerByEmail(dto.getEmail());
//		if (c.getResetPwd().equals("NO")) {
//			if (dto.getNewPwd().equals(dto.getConformPwd())) {
//				String oldPwd = dto.getOldPwd();
//				String dtoPwd = Pwdencode.encode(oldPwd);
//				String entityPwd = c.getOldPwd();
//				if (dtoPwd.equals(entityPwd)) {
//					cserv.resetPwd(dto);
//				}
//		}
//			else {
//				
//			}
//		
//			
//		} 
//		
//		Boolean iscompleted = cserv.resetPwd(dto);
//		
//		
//		return null;
//	}
//	

}
