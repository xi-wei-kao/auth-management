package com.java1234.dto;

import io.jsonwebtoken.Claims;
import lombok.Data;

@Data
public class JwtCheckResultDTO {

	private Integer errorCode;
	
	private Boolean isSuccess;
	
	private Claims claims;
	
}
