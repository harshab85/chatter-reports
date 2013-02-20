package com.application.chatter.tests.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.application.chatter.reports.MissingInputException;
import com.application.chatter.util.UserInfoUtil;

public class UserInfoUtilTest {

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
	public void testCreateUserFeedsDateKey_Negative1() {
		try {
			UserInfoUtil.createUserFeedsDateKey(null);
			fail("Should have thrown a MissingInputException");
		} 
		catch (MissingInputException e) {			
		}
	}
	
	@Test
	public void testCreateUserFeedsDateKey_Positive1() throws MissingInputException {
		
		String date = UserInfoUtil.createUserFeedsDateKey(new Date());
		assertNotNull(date);
		assertFalse(date.isEmpty());
		
		DateFormat f = new SimpleDateFormat(UserInfoUtil.DATEKEY_FORMAT);
		String expected = f.format(new Date());
		
		assertEquals(expected, date);
	}

	@Test
	public void testGetJSONResponse_Negative1() throws IOException, ParseException {
		try {
			UserInfoUtil.getJSONResponse(null, null);
			fail("Should have thrown a MissingInputException");
		} 
		catch (MissingInputException e) {
		}
	}
	
	@Test
	public void testGetJSONResponse_Negative2() throws IOException, ParseException {
		try {
			UserInfoUtil.getJSONResponse(null, "");
			fail("Should have thrown a MissingInputException");
		} 
		catch (MissingInputException e) {
		}
	}
	
	@Test
	public void testGetJSONResponse_Negative3() throws IOException, ParseException {
		try {
			UserInfoUtil.getJSONResponse("", null);
			fail("Should have thrown a MissingInputException");
		} 
		catch (MissingInputException e) {
		}
	}

	@Test
	public void testGetJSONResponse_Negative4() throws IOException, ParseException {
		try {
			UserInfoUtil.getJSONResponse("", "");
			fail("Should have thrown a MissingInputException");
		} 
		catch (MissingInputException e) {
		}
	}
	
	@Test
	public void testGetJSONResponse_Negative5() throws MissingInputException, ParseException {
		try {
			UserInfoUtil.getJSONResponse("abc", "def");
			fail("Should have thrown a IOException");
		} 
		catch (IOException e) {
		}
	}
	
	@Test
	public void testGetJSONResponse_Negative6() throws MissingInputException, IOException {
		try {
			UserInfoUtil.getJSONResponse("http://www.google.com", "def");
			fail("Should have thrown a ParseException");
		} 
		catch (ParseException e) {
		}
	}
	
	
}
