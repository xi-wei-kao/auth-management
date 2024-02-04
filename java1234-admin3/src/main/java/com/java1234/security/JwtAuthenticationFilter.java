package com.java1234.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.java1234.dao.TestUserMtrDao;
import com.java1234.dao.entity.TestUserMtrEnt;
import com.java1234.dto.JwtCheckResultDTO;
import com.java1234.utils.JwtUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
	
	@Autowired
	private TestUserMtrDao testUserMtrDao;
	
	@Autowired
	private CustUserDetailsServiceImpl custUserDetailsServiceImpl;

	private static final String URL_WHITELIST[] = {
		"/login",
		"/logout",
		"/captcha",
		"/password",
		"/image/**",
//		"/test/**"
	};
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String token = request.getHeader("token");
		System.out.println("請求 url: " + request.getRequestURI());
		
		Optional<String> tokenOpt = Optional.ofNullable(token);
		// 若 token 為空 || 請求為白名單, 放行至之後 Filter 處理
		if (tokenOpt.isEmpty() || Arrays.asList(URL_WHITELIST).contains(request.getRequestURI())) {
			chain.doFilter(request, response);
			return;
		}
		
		// 若 token 非空, 進行驗證 JWT【不成功例外處理】
		JwtCheckResultDTO checkResult = JwtUtils.validateJWT(token);
		if (!checkResult.getIsSuccess()) {
			switch (checkResult.getErrorCode()) {
				case 4000   : throw new JwtException("Token 不存在!");
				case 4001   : throw new JwtException("Token 驗證不通過!");
				case 4002   : throw new JwtException("Token 過期!");
			}
		}
		
		// JWT 驗證成功, 取回驗證主體(Claims)用戶名(Subject)
		Claims claims = JwtUtils.parseJWT(token);
		String username = claims.getSubject();
		
		// 以用戶名取得用戶實體資訊(from DB)
		TestUserMtrEnt userEnt = testUserMtrDao.findByUserName(username);
		
		// 將用戶認證資訊加入 Spring-Security 上下文
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String pwd = passwordEncoder.encode(userEnt.getUserPassword());
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
				new UsernamePasswordAuthenticationToken(
						userEnt.getUserName(),
						pwd,
						custUserDetailsServiceImpl.getUserAuthority());
//		usernamePasswordAuthenticationToken.setAuthenticated(checkResult.getIsSuccess());
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		
		chain.doFilter(request, response);
		
//		super.doFilterInternal(request, response, chain);
	}

}
