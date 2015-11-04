package com.revoter.rest.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.revoter.model.User;
import com.revoter.rest.repository.UserRepository;

@Component
public class RestaurantUserDetailsService implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantUserDetailsService.class);
	
	@Autowired
	private UserRepository userRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			LOGGER.error("User with the username '{}' doesn't exist!", username);
			throw new UsernameNotFoundException("User with the username '" + username + "' doesn't exist!");
		}
		
		// Create a granted authority based on user's role.
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		String role = user.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
		LOGGER.info("'{}' has role {}", username, role);
		authorities = AuthorityUtils.createAuthorityList(role);

		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
		return userDetails;
	}
}