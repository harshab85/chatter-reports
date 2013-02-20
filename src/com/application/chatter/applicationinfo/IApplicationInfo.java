package com.application.chatter.applicationinfo;

import com.application.chatter.authentication.IAuthenticate.IToken;

public interface IApplicationInfo{
		
	public IToken getToken();
	
	public String getInstanceURL();
		
	public String getIdentityServiceURL();
	
}