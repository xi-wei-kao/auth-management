package com.java1234.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

/**
 * 簡單儲存權限內容, 不開 TABLE
 * @author aa349
 *
 */
@Component
public class TestAuthorityStore {
	
	/**
	 * 取得使用者, 角色定義授權範圍表示字串
	 * 規定格式: ROLE_ADMIN, ROLE_COMMON, system_user:resetPwd, system_role_delete...
	 * @return
	 */
	public String getUserAuthorityInfo(Long userId) {
		StringBuilder authorityBuilder = new StringBuilder();
		
		// [Step]: 取得使用者id下, 所有角色清單
		List<String> userRoleList = getRoleListByUser(userId);
		// (Step-task) ==> 取得規定格式角色拼接字串「ROLE_ADMIN, ROLE_COMMON」
		String roleLocalStr = userRoleList.stream()
				.map(role -> "ROLE_" + role)
				.collect(Collectors.joining(","));
		
		authorityBuilder.append(roleLocalStr);
		
		
		// [Step]: 拜訪角色清單, 取得各角色權限清單, 且處理不重複
		Set<String> totalCodeSet = new HashSet<>();
		
		// (Step-task1) ==> 整理各角色權限清單字串, 且加入至不重複 SET   
		for (String role : userRoleList) {
			List<String> authCodeList = getAuthCodeList(role);
			
			totalCodeSet.addAll(authCodeList);
		}
		
		// (Step-task2) ==> 將不重複 SET, 組合為目標列表格式字串
		String authStrs = totalCodeSet.stream()
				.collect(Collectors.joining(","));
		
		authorityBuilder
			.append(",")
			.append(authStrs);
		
		
		System.out.println("authorityBuilder.toString() = " + authorityBuilder.toString());
		return authorityBuilder.toString();
	}
	
	/**
	 * 模擬取得使用者角色清單
	 * @param userId
	 * @return
	 */
	private List<String> getRoleListByUser(Long userId) {
		
		List<String> roleList = new ArrayList<>();
		roleList.add("ADMIN");
		roleList.add("COMMON");
		
		return roleList;
		
	}
	
	private final static String[] ADMIN_AUTH = {
			"system:role:userManagement",          // 用戶管理
			"system:role:cardManagement",          // 卡片管理
			"system:role:energyManagement",        // 能源管理
			"system:role:equipManagement",         // 設備管理
			"system:role:systemManagement"         // 系統管理
	};
	
	private final static String[] COMMON_AUTH = {
			"system:role:energyManagement",        // 能源管理
			"system:role:equipManagement",         // 設備管理
	};
	
	/**
	 * 模擬使用角色名稱, 取得該角色權限清單
	 * @param role
	 * @return
	 */
	private List<String> getAuthCodeList(String role) {
		switch (role) {
		case "ADMIN":
			return Arrays.asList(ADMIN_AUTH);
		case "COMMON":
			return Arrays.asList(COMMON_AUTH);
		default:
			return null;
		}

	}
	

	
	
}
