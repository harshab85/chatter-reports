package com.application.chatter.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.application.chatter.applicationinfo.IApplicationInfo;

public interface IAuthenticate {

	public IApplicationInfo authenticate(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException;
	
	interface IToken {

		public String getValue();
		
		public boolean isAuthenticated();
	}		
}
