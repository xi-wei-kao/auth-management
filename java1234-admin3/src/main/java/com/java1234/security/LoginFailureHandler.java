package com.java1234.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

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
public class LoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		response.setContentType("application/json;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();
		
		String message = exception.getMessage();
		// 驗證錯誤, 修改為可讀訊息
		if (exception instanceof BadCredentialsException) {
			message = "用戶名或密碼錯誤!";
		}
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("message", message);
		resultMap.put("code", "500");
		
		Gson gson = new Gson();
		outputStream.write(gson.toJson(resultMap).getBytes("UTF-8"));
		outputStream.flush();
		outputStream.close();
		
	}
	
}
