package com.application.chatter.util;

import java.io.IOException;
import java.util.Properties;

import com.application.chatter.authentication.AuthenticationException;

public final class AuthenticationUtil {

	public static final String AUTH_PROPERTY_FILE_NAME = "OAuth.properties";
	
	public static final String URL_PARAM_DELIMITER = "&";
	
	public static final String URL_PARAM_BEGIN_DELIMITER = "?";
	
	/**
	 * Reads the OAuth properties file for user authentication
	 * 
	 * @throws AuthenticationException
	 */
	public static Properties readAuthProperties() throws AuthenticationException{
		try {
			return ApplicationUtil.readApplicationProperties(AUTH_PROPERTY_FILE_NAME);
		} 
		catch (IOException e) {
			throw new AuthenticationException(ApplicationUtil.getErrorMessage(e));
		}
	}
	
	/**
	 * Enum to store the authentication request/response parameters
	 * 
	 * @author hbalasubramanian
	 *
	 */
	public enum AuthenticationParams{
		
		CODE("code"), ACCESSTOKEN("access_token"), INSTANCE_URL("instance_url"), IDENTITY_SERVICE("id");
		
		String paramName = null;
		
		AuthenticationParams(String paramName){
			this.paramName = paramName;
		}
		
		public String getParamName(){
			return this.paramName;
		}
	}
	
	/**
	 * Enum to store all the keys in the OAuth properties file
	 * 
	 * @author hbalasubramanian
	 *
	 */
	public enum AuthPropertyFileKeys{
		
		AUTHENTICATION_URL("AUTHENTICATION_URL"), RESPONSE_TYPE("RESPONSE_TYPE"),
		CLIENT_ID("CLIENT_ID"), DISPLAY("DISPLAY"), SCOPE("SCOPE"), REDIRECT_URI("RU"),
		AUTHORIZATION_URL("AUTHORIZATION_URL"), GRANT_TYPE("GRANT_TYPE"),
		CLIENT_SECRET("CLIENT_SECRET");
		
		String key = null;
		
		AuthPropertyFileKeys(String key){
			this.key = key;
		}
		
		public String getKey(){
			return this.key;
		}
		
		public String getParamName(){
			return this.name().toLowerCase();
		}
	}
}
