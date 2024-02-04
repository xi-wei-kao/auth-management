package com.java1234.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 自定義 Logout 處理程序
 * @author aa349
 *
 */
@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		response.setContentType("application/json;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("message", "登出成功!");
		resultMap.put("code", "200");
		 
		Gson gson = new Gson();
		outputStream.write(gson.toJson(resultMap).getBytes());
		outputStream.flush();
		outputStream.close();
		
	}

}
