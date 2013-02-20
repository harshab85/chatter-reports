package com.application.chatter.authentication.oauth;

import com.application.chatter.authentication.IAuthenticate.IToken;

public class OAuthToken implements IToken {

	private String token = null;
	private boolean isAuthenticated = false; 
	
	public OAuthToken(String token, boolean isAuthenticated) {
		this.token = token;
		this.isAuthenticated = isAuthenticated;
	}
	
	@Override
	public String getValue() {
		return this.token;
	}

	@Override
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

}
