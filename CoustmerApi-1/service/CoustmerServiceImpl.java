package com.service;

import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ModelMapper.CoustmerModelMapper;
import com.auth.Authresponse;
import com.dto.CoustmerDTO;
import com.dto.ResetPwdDTO;
import com.entity.Coustmer;
import com.mail.EmailSender;
import com.repo.CoustmerRepo;

@Service
public class CoustmerServiceImpl implements CoustmerService, UserDetailsService {

	private final CoustmerService coustmerService;

	@Autowired
	private CoustmerRepo crepo;
	
	@Autowired
	private BCryptPasswordEncoder PwdEncode;
	
	@Autowired
	private EmailSender mailsend;
	
	@Autowired
	private AuthenticationManager authmanager;

	CoustmerServiceImpl(CoustmerService coustmerService) {
		this.coustmerService = coustmerService;
	}

	@Override
	public Boolean register(CoustmerDTO dto) {
//		if (dto != null) {
//			Coustmer entity = CoustmerModelMapper.cvrDtoToEntity(dto);
//			Coustmer save = crepo.save(entity);
//			return save != null;
//		}
		
		String org = generatePWd();
		String encode = PwdEncode.encode(org);
		Coustmer entity = CoustmerModelMapper.cvrDtoToEntity(dto);
		entity.setOldPwd(encode);
		entity.setResetPwd("NO");
		Coustmer save = crepo.save(entity);
		if (save.getId()!=null) {
			String subject = "Registration Successful";

	    	String body = "Hello " + dto.getName() + ",\n\n" 
	    	        + "Your temporary password is: " + org + "\n\n"
	    	        + "Please login and change your password.\n\n"
	    	        + "Regards,\nTeam";
	    return mailsend.mailsend(dto.getEmail(), subject, body);
	    	
		}
		return false;
	}

	@Override
	public Boolean isUniqueEmail(String email) {
		Coustmer c = crepo.findByEmail(email);
		return c == null;
	}

	@Override
	public CoustmerDTO getCoustmerByEmail(String email) {
		Coustmer coustmer = crepo.findByEmail(email);
		if (coustmer != null) {
			CoustmerDTO dto = CoustmerModelMapper.cvrtEntityToDto(coustmer);
			return dto;
		}
		return null;
	}

	@Override
	public Boolean resetPwd(ResetPwdDTO rdto) {
	
		Coustmer c = crepo.findByEmail(rdto.getEmail());
		c.setOldPwd(rdto.getNewPwd());
		c.setResetPwd("YES");
		Coustmer save = crepo.save(c);
		
		return save!=null;
	}

	@Override
	public Authresponse login(CoustmerDTO dto) {
	
		Authresponse response= new Authresponse();
		UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getOldPwd());
		Authentication authenticate = authmanager.authenticate(token);
		if (authenticate.isAuthenticated()) {
			Coustmer entity = crepo.findByEmail(dto.getEmail());
			response.setCoustmerdto(CoustmerModelMapper.cvrtEntityToDto(entity));
			response.setToken("");
			return response;
		}
		
		return response;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Coustmer c = crepo.findByEmail(email);
		return new User(c.getEmail(), c.getOldPwd(), Collections.emptyList());
	}

	private String generatePWd() {
		String saltchar="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		Random ran= new Random();
		StringBuilder pwd= new StringBuilder();
		while (pwd.length()<5) {
		int index=ran.nextInt()*saltchar.length();
		pwd.append(saltchar.charAt(index));
			
		}
		return pwd.toString();
	}

	@Override
	public Boolean forgotPwd(String email) {
		Coustmer byEmail = crepo.findByEmail(email);
		if (byEmail!=null) {
			
			String subject="rest Password";
			String body="Template";
			 mailsend.mailsend(email, subject, body);
			return true;	
		}
		return false;
	}
}
