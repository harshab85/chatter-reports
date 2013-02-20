package com.application.chatter.tests.reports;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.application.chatter.applicationinfo.IApplicationInfo;
import com.application.chatter.authentication.IAuthenticate.IToken;
import com.application.chatter.feeds.FeedException;
import com.application.chatter.profile.ProfileReadException;
import com.application.chatter.reports.FeedsReportGenerator;
import com.application.chatter.reports.ReportException;

public class FeedsReportGeneratorTest {

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
	public void testGetReport_Negative1() throws ProfileReadException, FeedException {
		
		try {
			FeedsReportGenerator.getReport(null, null, null);
			fail("Should have thrown a ReportException");
		} catch (ReportException e) {			
		}		
	}
	
	@Test
	public void testGetReport_Negative2() throws ProfileReadException, ReportException, FeedException {
		
		try {
			IApplicationInfo info = new IApplicationInfo() {
				
				@Override
				public IToken getToken() {					
					return null;
				}
				
				@Override
				public String getInstanceURL() {					
					return null;
				}
				
				@Override
				public String getIdentityServiceURL() {					
					return null;
				}
			};
			
			FeedsReportGenerator.getReport(info, "ADVANCED", null);
			fail("Should have thrown a UnsupportedOperationException");
		} catch (UnsupportedOperationException e) {			
		}
		
	}		

}
