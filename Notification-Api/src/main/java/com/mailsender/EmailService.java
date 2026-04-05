package com.mailsender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailsender;
	
	public boolean mailsender(String Subject,String body,String to) {
		boolean issent=false; 
		
		try {
			MimeMessage mimeMessage = mailsender.createMimeMessage();
			MimeMessageHelper helper = new  MimeMessageHelper(mimeMessage);
			helper.setTo(to);
			helper.setText(body);
			helper.setSubject(Subject);
			mailsender.send(mimeMessage);
			 return true;
			
		} catch (Exception e) {
        e.printStackTrace();
        return false;
		
		}
		
	}
}
