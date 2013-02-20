package com.application.chatter.tests.factory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.application.chatter.feeds.FeedsFactory;
import com.application.chatter.profile.ProfileFactory;

public class FactoryTest {

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
	public void testGetFactory1() {
		FeedsFactory instance1 = FeedsFactory.getFactory();
		FeedsFactory instance2 = FeedsFactory.getFactory();
		assertNotNull(instance1);
		assertNotNull(instance2);
		assertTrue(instance1 == instance2);
	}
	
	@Test
	public void testGetFactory2() {
		ProfileFactory instance1 = ProfileFactory.getFactory();
		ProfileFactory instance2 = ProfileFactory.getFactory();
		assertNotNull(instance1);
		assertNotNull(instance2);
		assertTrue(instance1 == instance2);
	}
}
