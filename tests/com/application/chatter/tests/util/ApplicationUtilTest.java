/**
 * 
 */
package com.application.chatter.tests.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Properties;

import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.application.chatter.reports.MissingInputException;
import com.application.chatter.tests.TestUtil;
import com.application.chatter.util.ApplicationUtil;
import com.application.chatter.util.AuthenticationUtil;

/**
 * @author hbalasubramanian
 *
 */
public class ApplicationUtilTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.application.chatter.util.ApplicationUtil#getErrorMessage(java.lang.Throwable)}.
	 */
	@Test
	public void testGetErrorMessageThrowable_Negative1() {				
		// Null
		String error = ApplicationUtil.getErrorMessage(null);
		assertTrue(error.equals("Unknown Error"));
	}
	
	@Test
	public void testGetErrorMessageThrowable_Negative2() {				
		// No error message
		Exception e = new Exception();
		String error = ApplicationUtil.getErrorMessage(e);
		assertNotNull(error);
		assertTrue(error.contains(TestUtil.EXCEPTION_1));
	}
	
	@Test
	public void testGetErrorMessageThrowable_Positive2() {				
		Exception e = new Exception("Unable to read file");
		String error = ApplicationUtil.getErrorMessage(e);
		assertTrue(error.contains(TestUtil.EXCEPTION_2));		
	}

	/**
	 * Test method for {@link com.application.chatter.util.ApplicationUtil#getErrorMessage(java.lang.String, java.lang.Throwable)}.
	 */
	@Test
	public void testGetErrorMessageStringThrowable_Negative1() {
		String error = ApplicationUtil.getErrorMessage(null, null);
		assertTrue(error.equals("Unknown Error"));
	}
	
	@Test
	public void testGetErrorMessageStringThrowable_Positive1() {		
		Exception e = new Exception();
		String error = ApplicationUtil.getErrorMessage(null, e);
		assertNotNull(error);
		assertTrue(error.contains(TestUtil.EXCEPTION_1));
	}
	
	@Test
	public void testGetErrorMessageStringThrowable_Positive2() {		
		Exception e = new Exception();
		String error = ApplicationUtil.getErrorMessage("", e);
		assertNotNull(error);
		assertTrue(error.contains(TestUtil.EXCEPTION_1));
	}
	
	@Test
	public void testGetErrorMessageStringThrowable_Positive3() {		
		Exception e = new Exception();
		String error = ApplicationUtil.getErrorMessage("Application Error", e);
		assertNotNull(error);
		assertTrue(error.contains(TestUtil.EXCEPTION_1));
		assertTrue(error.startsWith("Application Error"));
	}

	/**
	 * Test method for {@link com.application.chatter.util.ApplicationUtil#readApplicationProperties(java.lang.String)}.
	 * @throws IOException 
	 */
	@Test
	public void testReadApplicationProperties_Negative1() throws IOException {
		Properties properties = ApplicationUtil.readApplicationProperties(null);
		assertTrue(properties.isEmpty());
	}
	
	@Test
	public void testReadApplicationProperties_Negative2() throws IOException {
		Properties properties = ApplicationUtil.readApplicationProperties("");
		assertTrue(properties.isEmpty());
	}
	
	@Test
	public void testReadApplicationProperties_Negative3() throws IOException {
		Properties properties = ApplicationUtil.readApplicationProperties(AuthenticationUtil.AUTH_PROPERTY_FILE_NAME);
		assertFalse(properties.isEmpty());
	}

	/**
	 * Test method for {@link com.application.chatter.util.ApplicationUtil#encode(java.lang.String)}.
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void testEncode_Negative1() throws UnsupportedEncodingException {
		String encodedString = ApplicationUtil.encode(null);
		assertNull(encodedString);
	}
	
	@Test
	public void testEncode_Negative2() throws UnsupportedEncodingException {
		String encodedString = ApplicationUtil.encode("");
		assertTrue(encodedString.isEmpty());
	}
	
	@Test
	public void testEncode_Positive1() throws UnsupportedEncodingException {
		String encodedString = ApplicationUtil.encode("abc");
		assertEquals("abc", encodedString);
	}
	
	@Test
	public void testEncode_Positive2() throws UnsupportedEncodingException {
		String encodedString = ApplicationUtil.encode(TestUtil.REDIRECTURL);
		String expected = URLEncoder.encode(TestUtil.REDIRECTURL, "UTF-8");
		assertEquals(expected, encodedString);
	}

	/**
	 * Test method for {@link com.application.chatter.util.ApplicationUtil#getJSONResponse(java.net.URLConnection)}.
	 * @throws MissingInputException 
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@Test
	public void testGetJSONResponseURLConnection_Negative() throws IOException, ParseException {
		
		URLConnection connection = null;
		try {
			ApplicationUtil.getJSONResponse(connection);
			fail("Should have thrown a MissingInputException");
		} 
		catch (MissingInputException e) {			
		}
	}

	/**
	 * Test method for {@link com.application.chatter.util.ApplicationUtil#getJSONResponse(java.lang.String)}.
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@Test
	public void testGetJSONResponseString_Negative1() throws IOException, ParseException {
		String url = null;
		try {
			ApplicationUtil.getJSONResponse(url);
			fail("Should have thrown a MissingInputException");
		} 
		catch (MissingInputException e) {			
		}
	}
	
	@Test
	public void testGetJSONResponseString_Negative2() throws IOException, ParseException {
		String url = null;
		try {
			ApplicationUtil.getJSONResponse("");
			fail("Should have thrown a MissingInputException");
		} 
		catch (MissingInputException e) {			
		}
	}

}
