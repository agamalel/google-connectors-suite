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
import org.mule.module.google.calendar.model.CalendarList;

public class UpdateCalendarListTestCases extends GoogleCalendarTestParent{
	
	
	@Before
	public void setUp() {
		try {
			addToMessageTestObject((Map<String, Object>) context.getBean("updateCalendarList"));
	
			Calendar calendar = runFlowAndGetPayload("create-calendar");
						
			addToMessageTestObject("calendar", calendar);
			addToMessageTestObject("id", calendar.getId());
			
			//Get Calendar List 
			CalendarList returnedCalendarList = runFlowAndGetPayload("get-calendar-list-by-id");
			
			addToMessageTestObject("calendarList", returnedCalendarList);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	@Category({RegressionTests.class})
	@Test
	public void testUpdateCalendarList() {
		try {
			String colorAfter = runFlowAndGetPayload("colorAfter");
			
			CalendarList returnedCalendarList = getValueFromMessageTestObject("calendarList");
			
			returnedCalendarList.setColorId(colorAfter);
			addToMessageTestObject("calendarListRef", returnedCalendarList);
			
			CalendarList afterUpdate = runFlowAndGetPayload("update-calendar-list");
			String afterColorId = afterUpdate.getColorId();
			assertEquals(afterColorId, colorAfter);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String calendarId = getValueFromMessageTestObject("id");
			deleteCalendar(calendarId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	

}
