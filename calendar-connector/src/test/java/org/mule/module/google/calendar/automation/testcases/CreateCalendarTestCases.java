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

public class CreateCalendarTestCases extends GoogleCalendarTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			addToMessageTestObject((Map<String, Object>) context.getBean("createCalendar"));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testCreateCalendar() {
		try {
			
			Calendar originalCalendar = getValueFromMessageTestObject("calendarRef");
			Calendar createdCalendar = runFlowAndGetPayload("create-calendar");

			assertTrue(createdCalendar != null);
			assertTrue(createdCalendar.getSummary().equals(originalCalendar.getSummary()));

			addToMessageTestObject("id", createdCalendar.getId());

			Calendar returnedCalendar = runFlowAndGetPayload("get-calendar-by-id");
			assertTrue(returnedCalendar != null);
			assertTrue(returnedCalendar.getId().equals(createdCalendar.getId()));
		}
		catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			runFlowAndGetPayload("delete-calendar");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
