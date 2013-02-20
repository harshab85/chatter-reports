package com.application.chatter.authentication.oauth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.application.chatter.applicationinfo.IApplicationInfo;
import com.application.chatter.authentication.AuthenticationException;
import com.application.chatter.authentication.IAuthenticate;
import com.application.chatter.util.ApplicationUtil;
import com.application.chatter.util.AuthenticationUtil;
import com.application.chatter.util.AuthenticationUtil.AuthPropertyFileKeys;
import com.application.chatter.util.AuthenticationUtil.AuthenticationParams;

/**
 * An implementation of {@link IAuthenticate} which authenticates the current user using OAuth 2.0
 * 
 * <br>
 * The object of this class is a Singleton since the properties required to authenticate users 
 * need to be read and initialized only once
 * 
 * @author hbalasubramanian
 *
 */
public class OAuthUserAuthenticator implements IAuthenticate {

	private static OAuthUserAuthenticator authenticator = null;
	
	private OAuthProperties oAuthProperties = null;
	
	private IApplicationInfo applicationInfo = null;
	
	private OAuthUserAuthenticator() throws AuthenticationException{				
		
		if(oAuthProperties == null){			
			Properties properties = AuthenticationUtil.readAuthProperties();
			oAuthProperties = new OAuthProperties(properties);
		}
	}

	/**
	 * Utility class for storing the OAuth properties
	 * 
	 * @author hbalasubramanian
	 *
	 */
	class OAuthProperties{
		
		private Properties oAuthProperties = null;
		
		OAuthProperties(Properties oAuthProperties){
			this.oAuthProperties = oAuthProperties;
		}
		
		public String getAuthenticationUrl(){
			return this.oAuthProperties.getProperty(AuthPropertyFileKeys.AUTHENTICATION_URL.getKey());
		}
		
		public String getResponseType(){
			return this.oAuthProperties.getProperty(AuthPropertyFileKeys.RESPONSE_TYPE.getKey());
		}
		
		public String getClientId(){
			return this.oAuthProperties.getProperty(AuthPropertyFileKeys.CLIENT_ID.getKey());
		}
		
		public String getDisplay(){
			return this.oAuthProperties.getProperty(AuthPropertyFileKeys.DISPLAY.getKey());
		}
		
		public String getScope(){
			return this.oAuthProperties.getProperty(AuthPropertyFileKeys.SCOPE.getKey());
		}
		
		public String getRedirectURI(){
			return this.oAuthProperties.getProperty(AuthPropertyFileKeys.REDIRECT_URI.getKey());
		}
		
		public String getAuthorizationURL(){
			return this.oAuthProperties.getProperty(AuthPropertyFileKeys.AUTHORIZATION_URL.getKey());
		}
		
		public String getGrantType(){
			return this.oAuthProperties.getProperty(AuthPropertyFileKeys.GRANT_TYPE.getKey());
		}
		
		public String getClientSecret(){
			return this.oAuthProperties.getProperty(AuthPropertyFileKeys.CLIENT_SECRET.getKey());
		}
	}
	
	/**
	 * Double locked Singleton instance
	 * 
	 * @return
	 * @throws AuthenticationException 
	 * @throws IOException 
	 */
	public static final OAuthUserAuthenticator getInstance() throws AuthenticationException{		
		if(authenticator == null){			
			synchronized (OAuthUserAuthenticator.class) {
				if(authenticator == null){
					authenticator = new OAuthUserAuthenticator();
				}
			}			
		}
		
		return authenticator;
	}

	/**
	 * This method is called ONLY once during user authentication. 
	 * 
	 * <br><br>
	 * 
	 * <b>First Invocation</b> - Client code is null. Redirect client to Authentication URL
	 * <br>
	 * <b>Second Invocation</b> - Client code is present. Invoke the authorization URL using the code and client secret.
	 * <br>
	 * Get the access token from the response payload on success
	 */
	@Override
	public IApplicationInfo authenticate(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
	
		String code = request.getParameter(AuthenticationParams.CODE.getParamName());		
		try{
			if(code == null || code.isEmpty()){				
				applicationInfo = null;
				String authenticationUrl = getAuthenticationURL();
				response.sendRedirect(authenticationUrl);				
			}
			else {
				String authorizationUrl = getAuthorizationURL(code);
				final JSONObject json = ApplicationUtil.getJSONResponse(authorizationUrl);
					
				if(json != null){
					applicationInfo = new IApplicationInfo() {
									
						@Override
						public String getIdentityServiceURL() {
							return (String)json.get(AuthenticationParams.IDENTITY_SERVICE.getParamName());
						}
							
						@Override
						public String getInstanceURL() {
							return (String)json.get(AuthenticationParams.INSTANCE_URL.getParamName());
						}

						@Override
						public IToken getToken() {							
							String accessToken = (String)json.get(AuthenticationParams.ACCESSTOKEN.getParamName());
							return new OAuthToken(accessToken, true);
						}
					};											
				}
				else{
					throw new AuthenticationException("Authentication Failed. Unable to retrieve the access token and application information");
				}
			}
		}
		catch (Exception e) {
			throw new AuthenticationException(ApplicationUtil.getErrorMessage(e));
		}
		
		return applicationInfo;
	}

	/**
	 * Constructs the Authorization URL
	 */
	public String getAuthorizationURL(String code) throws UnsupportedEncodingException{
		
		StringBuilder authorizationUrl = new StringBuilder();
		authorizationUrl.append(oAuthProperties.getAuthorizationURL());
		authorizationUrl.append(AuthenticationUtil.URL_PARAM_BEGIN_DELIMITER);
		authorizationUrl.append(AuthenticationParams.CODE.getParamName());
		authorizationUrl.append("=");
		authorizationUrl.append(code);
		authorizationUrl.append(AuthenticationUtil.URL_PARAM_DELIMITER);
		authorizationUrl.append(AuthPropertyFileKeys.GRANT_TYPE.getParamName());
		authorizationUrl.append("=");
		authorizationUrl.append(oAuthProperties.getGrantType());
		authorizationUrl.append(AuthenticationUtil.URL_PARAM_DELIMITER);
		authorizationUrl.append(AuthPropertyFileKeys.CLIENT_ID.getParamName());
		authorizationUrl.append("=");
		authorizationUrl.append(oAuthProperties.getClientId());
		authorizationUrl.append(AuthenticationUtil.URL_PARAM_DELIMITER);
		authorizationUrl.append(AuthPropertyFileKeys.CLIENT_SECRET.getParamName());
		authorizationUrl.append("=");
		authorizationUrl.append(oAuthProperties.getClientSecret());
		authorizationUrl.append(AuthenticationUtil.URL_PARAM_DELIMITER);
		authorizationUrl.append(AuthPropertyFileKeys.REDIRECT_URI.getParamName());
		authorizationUrl.append("=");
		
		String redirectURI = ApplicationUtil.encode(oAuthProperties.getRedirectURI());
		authorizationUrl.append(redirectURI);
		
		return authorizationUrl.toString();
	}
	
	/**
	 * Constructs the Authentication URL
	 */
	public String getAuthenticationURL() throws UnsupportedEncodingException {
		
		StringBuilder authenticationUrl = new StringBuilder();
		authenticationUrl.append(oAuthProperties.getAuthenticationUrl());
		authenticationUrl.append(AuthenticationUtil.URL_PARAM_BEGIN_DELIMITER);
		authenticationUrl.append(AuthPropertyFileKeys.RESPONSE_TYPE.getParamName());
		authenticationUrl.append("=");
		authenticationUrl.append(oAuthProperties.getResponseType());
		authenticationUrl.append(AuthenticationUtil.URL_PARAM_DELIMITER);
		authenticationUrl.append(AuthPropertyFileKeys.CLIENT_ID.getParamName());
		authenticationUrl.append("=");
		authenticationUrl.append(oAuthProperties.getClientId());
		authenticationUrl.append(AuthenticationUtil.URL_PARAM_DELIMITER);
		authenticationUrl.append(AuthPropertyFileKeys.DISPLAY.getParamName());
		authenticationUrl.append("=");
		authenticationUrl.append(oAuthProperties.getDisplay());
		authenticationUrl.append(AuthenticationUtil.URL_PARAM_DELIMITER);
		authenticationUrl.append(AuthPropertyFileKeys.REDIRECT_URI.getParamName());
		authenticationUrl.append("=");
		
		String redirectURI = ApplicationUtil.encode(oAuthProperties.getRedirectURI());
		authenticationUrl.append(redirectURI);
		
		return authenticationUrl.toString();
	}

}
