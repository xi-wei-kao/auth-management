package com.java1234.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.java1234.dao.TestUserMtrDao;
import com.java1234.dao.entity.TestUserMtrEnt;

/**
 * 自定義 UserDetails
 * @author aa349
 *
 */
@Service
public class CustUserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private TestUserMtrDao testUserMtrDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		TestUserMtrEnt ent = testUserMtrDao.findByUserName(username);
		Optional<TestUserMtrEnt> entOpt = Optional.ofNullable(ent);
		
		if (entOpt.isEmpty()) {
			throw new UsernameNotFoundException("用戶名或密碼錯誤!");
		}
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String pwd = passwordEncoder.encode(ent.getUserPassword());
		
		return new User(ent.getUserName(), pwd, getUserAuthority());
	}

	private List<GrantedAuthority> getUserAuthority() {
		return new ArrayList<>();
	}

}
