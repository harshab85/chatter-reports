package com.application.chatter.tests.oauth;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.application.chatter.authentication.AuthenticationException;
import com.application.chatter.authentication.IAuthenticate;
import com.application.chatter.authentication.oauth.OAuthUserAuthenticator;

public class OAuthTest {

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
	public void testGetInstance() throws AuthenticationException {
		IAuthenticate instance1 = OAuthUserAuthenticator.getInstance();
		IAuthenticate instance2 = OAuthUserAuthenticator.getInstance();
		
		assertNotNull(instance1);
		assertNotNull(instance2);
	}

	@Test
	public void testGetAuthenticationURL() throws AuthenticationException, UnsupportedEncodingException {
		OAuthUserAuthenticator auth = OAuthUserAuthenticator.getInstance();
		String url = auth.getAuthenticationURL();
		assertFalse(url.isEmpty());
	}

	@Test
	public void testGetAuthorizationURL() throws AuthenticationException, UnsupportedEncodingException {
		OAuthUserAuthenticator auth = OAuthUserAuthenticator.getInstance();
		String url = auth.getAuthorizationURL("abc");
		assertFalse(url.isEmpty());
	}
	
}
