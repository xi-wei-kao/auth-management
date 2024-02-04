package com.java1234.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.java1234.security.JwtAuthenticationEntryPoint;
import com.java1234.security.JwtAuthenticationFilter;
import com.java1234.security.LoginFailureHandler;
import com.java1234.security.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	private LoginFailureHandler loginFailureHandler;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	private static final String URL_WHITELIST[] = {
		"/login",
		"/logout",
		"/captcha",
		"/password",
		"/image/**",
//		"/test/**"
	};
	
	@Bean
	AuthenticationManager authenticationManager() {
		return new AuthenticationManager() {
			
			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				return authentication;
			}
		};
	}
	
	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter() {
		
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
		return jwtAuthenticationFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			// CSRF 設置
			.csrf(AbstractHttpConfigurer::disable)
	
			// 登入登出 設置
			.formLogin()
				.successHandler(loginSuccessHandler)
				.failureHandler(loginFailureHandler)
//			.and()
//				.logout()
//				.logoutSuccessHandler(null)
		
			// Session 設置
			.and()
				.sessionManagement()
				// Session 建立策略: 無狀態(前後端分離)
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			
			// 請求攔截規則 設置
			.and()
				.authorizeHttpRequests(registry ->
					registry
						// 允許白名單
						.requestMatchers(URL_WHITELIST).permitAll()
						.anyRequest()
						.authenticated()
							
				)
				
			// 自定義過濾器 設置
			.addFilter(jwtAuthenticationFilter())
			
			// 異常處理 設置
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				
		;
		return http.build();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider(
			UserDetailsService userDetailsService,
			BCryptPasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		
		return provider;
	}
	
	
	
	
	
	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http
//			.authorizeHttpRequests(registry ->
//					registry 
//							// ADMIN 可查詢所有使用者(APPUSER)
//							.requestMatchers(HttpMethod.GET, "appusers/getAll")
//								.hasAnyAuthority(TypeOfUserAuthority.ADMIN.toString())
//							// ADMIN、NORMAL 可查詢單一使用者(APPUSER)
//							.requestMatchers(HttpMethod.GET, "appusers/*?")
//								.hasAnyAuthority(TypeOfUserAuthority.ADMIN.toString(),
//												 TypeOfUserAuthority.NORMAL.toString())
//							// 其餘接口允許呼叫
//							.anyRequest().authenticated()
//							.anyRequest().permitAll()
//							
//			)
//	//		.formLogin(Customizer.withDefaults())
//			.csrf(AbstractHttpConfigurer::disable);
//	
//	return http.build();
//	}
}
