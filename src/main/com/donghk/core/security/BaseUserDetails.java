package com.donghk.core.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.donghk.dto.common.LoginUserDetail;

public class BaseUserDetails extends User {

	private static final long serialVersionUID = -2014858939062424433L;

	public BaseUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, true, true, true, true, authorities);
	}

	public BaseUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	private LoginUserDetail user;

	public LoginUserDetail getUser() {
		return user;
	}

	public void setUser(LoginUserDetail user) {
		this.user = user;
	}

}
