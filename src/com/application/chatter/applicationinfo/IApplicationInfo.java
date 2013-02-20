package com.application.chatter.applicationinfo;

import com.application.chatter.authentication.IAuthenticate.IToken;

/**
 * Interface to store the secure access token, server instance URL and the user's
 * identity service URL
 * 
 * @author hbalasubramanian
 *
 */
public interface IApplicationInfo{
		
	/**
	 * Returns the secure token needed for access APIs 
	 * 
	 */
	public IToken getToken();
	
	/**
	 * Returns the secure server's instance eURL to access APIs
	 * 
	 */
	public String getInstanceURL();
		
	/**
	 * Returns a URL to access the user's information
	 */
	public String getIdentityServiceURL();
	
}