package com.java1234.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;


@RestController
@RequestMapping(path = "/test")
@CrossOrigin(origins = "*")
public class TestController {

	@GetMapping(path = "/getList")
	@PreAuthorize("hasRole('ROLE_ADMIN2')")
//	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
//	@PreAuthorize("hasAuthority('system:role:userManagement')")
	public Map<String, String> getList(
			@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
			@Parameter(hidden = true) @RequestHeader HttpHeaders httpHeaders) {
		Map<String, String> resultMap = new HashMap<>();
		Optional<List<String>> headers = Optional.ofNullable(httpHeaders.get("token"));
		if (headers.isPresent()) {
			resultMap.put("result", "OK");
			resultMap.put("status", "200");
		} else {
			resultMap.put("result", "NO");
			resultMap.put("status", "401");
		}
		return resultMap;
		
	}
}
