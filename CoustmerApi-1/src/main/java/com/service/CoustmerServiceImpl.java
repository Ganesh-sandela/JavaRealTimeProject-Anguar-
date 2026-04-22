package com.service;


import java.util.Collections;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
import com.entity.Customer;
import com.jwt.JwtUtil;
import com.mail.EmailSender;
import com.repo.CoustmerRepo;

@Service
public class CoustmerServiceImpl implements CoustmerService, UserDetailsService {
    
	@Autowired
    private JwtUtil jwtutil;

	@Autowired
	private CoustmerRepo crepo;
	
	@Autowired
	private BCryptPasswordEncoder PwdEncode;
	
	@Autowired
	private EmailSender mailsend;
	
//	@Autowired
//	private AuthenticationManager authmanager;

	private final AuthenticationManager authmanager;

	@Autowired
	public CoustmerServiceImpl(@Lazy AuthenticationManager authmanager) {
	    this.authmanager = authmanager;
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
		Customer entity = CoustmerModelMapper.cvrDtoToEntity(dto);
		entity.setOldPwd(encode);
		entity.setResetPwd("NO");
		Customer save = crepo.save(entity);
		
		System.out.println("DTO Email: " + dto.getEmail());
		System.out.println("Entity Email: " + entity.getEmail());
		if (save.getId()!=null) {
			String subject = "Registration Successful";

	    	String body = "Hello " + dto.getName() + ",\n\n" 
	    	        + "Your temporary password is: " + org + "\n\n"
	    	        + "Please login and change your password.\n\n"
	    	        + "Regards,\nTeam";
	    return mailsend.mailsend(save.getEmail(), subject, body);
	    	
		}
		return false;
	}

	@Override
	public Boolean isUniqueEmail(String email) {
		Customer c = crepo.findByEmail(email);
		return c == null;
	}

	@Override
	public CoustmerDTO getCoustmerByEmail(String email) {
		Customer coustmer = crepo.findByEmail(email);
		if (coustmer != null) {
			CoustmerDTO dto = CoustmerModelMapper.cvrtEntityToDto(coustmer);
			return dto;
		}
		return null;
	}

	@Override
	public Boolean resetPwd(ResetPwdDTO rdto) {
	
		Customer c = crepo.findByEmail(rdto.getEmail());
		  String encodedPwd = PwdEncode.encode(rdto.getNewPwd());
		  System.out.println("88888888888888888888888");
		  System.out.println(encodedPwd);
		    c.setOldPwd(encodedPwd);
		c.setResetPwd("YES");
		Customer save = crepo.save(c);
		
		return save!=null;
	}

	@Override
	public Authresponse login(CoustmerDTO dto) {
		
		System.out.println("====================="+dto);

	    try {
	        UsernamePasswordAuthenticationToken token =
	                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getOldPwd());

	        Authentication authenticate = authmanager.authenticate(token);
                     System.out.println("#@###########"+authenticate);
	        if (authenticate.isAuthenticated()) {
	            Customer entity = crepo.findByEmail(dto.getEmail());
                      String  jwt = jwtutil.generateToken(dto.getEmail());
                      
	            Authresponse response = new Authresponse();
	            response.setCoustmerdto(CoustmerModelMapper.cvrtEntityToDto(entity));
	            response.setToken(jwt); // later JWT

	            return response;
	        }

	    } catch (Exception e) {
	        System.out.println("Login failed: " + e.getMessage());
	    }

	    return null; // 🔥 important
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Customer c = crepo.findByEmail(email);
		return new User(c.getEmail(), c.getOldPwd(), Collections.emptyList());
		
	}

	private String generatePWd() {
		String saltchar="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		Random ran= new Random();
		StringBuilder pwd= new StringBuilder();
		while (pwd.length()<5) {
		int index=ran.nextInt(saltchar.length());
		pwd.append(saltchar.charAt(index));
			
		}
		return pwd.toString();
	}

	@Override
	public Boolean forgotPwd(String email) {
		Customer byEmail = crepo.findByEmail(email);
		System.out.println("Email received: " + email);
		if (byEmail!=null) {
			String oldPwd = byEmail.getOldPwd();
			 String pWd = generatePWd();
			 String encode = PwdEncode.encode(pWd);
			 System.out.println(encode+"..........");
			 byEmail.setOldPwd(encode);
			 crepo.save(byEmail);  
			String subject="forgot Password";
			String body="Your Password is "+pWd;
			 mailsend.mailsend(email, subject, body);
			return true;	
		}
		return false;
	}
}
