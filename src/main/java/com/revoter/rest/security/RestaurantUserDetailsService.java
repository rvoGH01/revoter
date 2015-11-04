package com.revoter.rest.security;

import java.util.ArrayList;
import java.util.List;

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
	@Autowired
	private UserRepository userRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User with the username " + username + " doesn't exist");
		}
		
		// Create a granted authority based on user's role.
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		String role = user.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
		authorities = AuthorityUtils.createAuthorityList(role);

		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
		return userDetails;
	}
}