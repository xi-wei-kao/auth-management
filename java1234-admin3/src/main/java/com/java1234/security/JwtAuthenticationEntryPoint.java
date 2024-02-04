package com.java1234.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.java1234.utils.JwtUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(401);
		ServletOutputStream outputStream = response.getOutputStream();
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("message", "認證失敗, 請登入!");
		resultMap.put("code", "401");
		
		Gson gson = new Gson();
		outputStream.write(gson.toJson(resultMap).getBytes());
		outputStream.flush();
		outputStream.close();
		
		
	}

}
