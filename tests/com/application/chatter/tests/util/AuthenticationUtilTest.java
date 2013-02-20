package com.application.chatter.tests.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.application.chatter.authentication.AuthenticationException;
import com.application.chatter.util.AuthenticationUtil;
import com.application.chatter.util.AuthenticationUtil.AuthPropertyFileKeys;

public class AuthenticationUtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadAuthProperties() throws AuthenticationException {
		Properties properties = AuthenticationUtil.readAuthProperties();
		assertNotNull(properties);
		assertFalse(properties.isEmpty());
		
		String authenticationURL = (String)properties.get(AuthPropertyFileKeys.AUTHENTICATION_URL.getKey());
		String authorizationURL = (String)properties.get(AuthPropertyFileKeys.AUTHORIZATION_URL.getKey());
		String clientId = (String)properties.get(AuthPropertyFileKeys.CLIENT_ID.getKey());
		String clientSecret = (String)properties.get(AuthPropertyFileKeys.CLIENT_SECRET.getKey());
		String display = (String)properties.get(AuthPropertyFileKeys.DISPLAY.getKey());
		String grantType = (String)properties.get(AuthPropertyFileKeys.GRANT_TYPE.getKey());
		String ru = (String)properties.get(AuthPropertyFileKeys.REDIRECT_URI.getKey());
		String responseType = (String)properties.get(AuthPropertyFileKeys.RESPONSE_TYPE.getKey());
		String scope = (String)properties.get(AuthPropertyFileKeys.SCOPE.getKey());
		
		assertNotNull(authenticationURL);
		assertNotNull(authorizationURL);
		assertNotNull(clientId);
		assertNotNull(clientSecret);
		assertNotNull(display);
		assertNotNull(grantType);
		assertNotNull(ru);
		assertNotNull(responseType);
		assertNotNull(scope);		
	}

}
