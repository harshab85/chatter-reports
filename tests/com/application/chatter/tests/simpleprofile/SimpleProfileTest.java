package com.application.chatter.tests.simpleprofile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.application.chatter.applicationinfo.IApplicationInfo;
import com.application.chatter.profile.IProfile;
import com.application.chatter.profile.simpleprofile.SimpleProfile;
import com.application.chatter.reports.MissingInputException;
import com.application.chatter.util.UserInfoUtil;

public class SimpleProfileTest {

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
	public void testCreateProfileData_Negative1() throws IOException, ParseException {
		
		SimpleProfileForTest simpleProfileTest = new SimpleProfileForTest(null);
		
		try {
			simpleProfileTest.createProfileData(null);
			fail("Should have thrown a MissingInputException");
		} 
		catch (MissingInputException e) {
		}
		
	}
	
	@Test
	public void testCreateProfileData_Negative2() throws IOException, ParseException, MissingInputException {
		
		JSONObject json = new JSONObject();
		SimpleProfileForTest simpleProfileTest = new SimpleProfileForTest(null);
		
		IProfile.IProfileData data = simpleProfileTest.createProfileData(json);
		
		assertNull(data.getFirstName());
		assertNull(data.getLastName());
		assertNull(data.getThumbnailURL());
		assertNull(data.getPhotoURL());
	}
	
	@Test
	public void testCreateProfileData_Positive1() throws IOException, ParseException, MissingInputException {
		
		Map<String, String> m = new HashMap<String, String>();
		m.put(UserInfoUtil.UserProfileKeys.FIRST_NAME.getKey(), "test1");
		m.put(UserInfoUtil.UserProfileKeys.LAST_NAME.getKey(), "test2");		
		
		JSONObject json = new JSONObject(m);
		SimpleProfileForTest simpleProfileTest = new SimpleProfileForTest(null);
		
		IProfile.IProfileData data = simpleProfileTest.createProfileData(json);
		
		assertEquals("test1", data.getFirstName());
		assertEquals("test2", data.getLastName());
	}
	
	class SimpleProfileForTest extends SimpleProfile{

		public SimpleProfileForTest(IApplicationInfo applicationInfo) {
			super(applicationInfo);		
		}
		
		@Override
		public IProfileData createProfileData(JSONObject json)
				throws IOException, ParseException, MissingInputException {			
			return super.createProfileData(json);
		}
		
	}

}
