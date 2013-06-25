/**
 * Mule Google Calendars Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.google.calendar.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.calendar.model.Calendar;

public class GetCalendarByIdTestCases extends GoogleCalendarTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getCalendarById");

			// Create the calendar
			MessageProcessor flow = lookupFlowConstruct("create-calendar");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Calendar calendar = (Calendar) response.getMessage().getPayload();
			testObjects.put("id", calendar.getId());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, SanityTests.class})
	@Test
	public void testGetCalendarById() {
		try {
			
			String createdCalendarId = testObjects.get("id").toString();
			
			MessageProcessor flow = lookupFlowConstruct("get-calendar-by-id");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			// Assertions on equality
			Calendar returnedCalendar = (Calendar) response.getMessage().getPayload();
			assertTrue(returnedCalendar != null);
			assertTrue(returnedCalendar.getId().equals(createdCalendarId));
		}
		catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			// Delete the calendar
			MessageProcessor flow = lookupFlowConstruct("delete-calendar");
			MuleEvent response = flow.process(getTestEvent(testObjects));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
