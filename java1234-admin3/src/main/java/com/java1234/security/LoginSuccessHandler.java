package com.java1234.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.java1234.utils.JwtUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 * 登入成功處理器
 * @author aa349
 *
 */

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request, 
			HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		response.setContentType("application/json;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		
		String username = authentication.getName();
		String token = JwtUtils.genJwtToken(username);
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("message", "登入成功");
		resultMap.put("authorization", token);
		resultMap.put("code", "200");
		
		Gson gson = new Gson();
		outputStream.write(gson.toJson(resultMap).getBytes());
		outputStream.flush();
		outputStream.close();
		
	}

}
