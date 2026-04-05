package com.entity;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Coustmer {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String email;
	private Long phno;
	
	private String conformPwd;
	private String newPwd;
	private String oldPwd;
	private String resetPwd;
	@CreationTimestamp
	private Date cretedDate;
	@UpdateTimestamp
	private Date LastUpdated;
	
	
}
