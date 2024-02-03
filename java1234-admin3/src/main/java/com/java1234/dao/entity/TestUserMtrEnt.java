package com.java1234.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TestUserMtrEnt {

	private Long id;
	
	private Long userId;
	
	private String userName;
	
	private String userPassword;
	
	private String email;
	
}
