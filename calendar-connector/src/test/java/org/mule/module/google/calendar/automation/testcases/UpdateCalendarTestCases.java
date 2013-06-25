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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.calendar.model.Calendar;
import org.mule.module.google.calendar.model.Event;

public class UpdateCalendarTestCases extends GoogleCalendarTestParent {
	
	
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("updateCalendar");
		
			// Insert the calendar
			Calendar calendar = insertCalendar((Calendar) testObjects.get("calendarRef"));
			
			
			// Update test objects
			testObjects.put("calendar", calendar);
			testObjects.put("id", calendar.getId());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testUpdateCalendar() {
		try {
			String summaryAfter = testObjects.get("summaryAfter").toString();
		
			Calendar calendar = (Calendar) testObjects.get("calendar");
			calendar.setSummary(summaryAfter);
			testObjects.put("calendarRef", calendar);
						
			MessageProcessor flow = lookupFlowConstruct("update-calendar");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Calendar afterUpdate = (Calendar) response.getMessage().getPayload();
			String afterText = afterUpdate.getSummary();
			assertEquals(afterText, summaryAfter);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String calendarId = testObjects.get("id").toString();
			deleteCalendar(calendarId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
