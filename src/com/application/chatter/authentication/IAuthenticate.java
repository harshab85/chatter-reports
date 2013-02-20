package com.application.chatter.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.application.chatter.applicationinfo.IApplicationInfo;

/**
 * Interface used to provide an authentication mechanism for the user. Contains the secure access token
 * required to access APIs after authentication
 * 
 * @author hbalasubramanian
 *
 */
public interface IAuthenticate {

	/**
	 * Implement this method to authenticate the user
	 * 
	 * @throws AuthenticationException
	 */
	public IApplicationInfo authenticate(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException;
	
	interface IToken {

		public String getValue();
		
		public boolean isAuthenticated();
	}		
}
