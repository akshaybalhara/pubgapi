package com.pubg.service.impl;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pubg.entity.UserEntity;
import com.pubg.repository.UserInfoRepository;

/**
 * JwtUserDetailsServiceImpl : Implementation class of interface UserDetailsService.
 * 
 * @author Prolifics
 *
 */
@Service("jwtUserDetailsService")
public class JwtUserDetailsServiceImpl implements UserDetailsService {
	
	/**
	 * userInfoRepository is injected by Spring Framework.
	 */
	@Autowired
	private UserInfoRepository userInfoRepository;

	/**
	 * loadUserByUsername queries a User by specified username and wraps
	 * it into Spring Security's User Object.
	 * 
	 * @param username 
	 * 	<p>The username to query from database.</p>
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	
	
		UserEntity user = userInfoRepository.getUserDetails(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(),
				new ArrayList<>());	
		
	}
}