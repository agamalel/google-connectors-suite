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

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.calendar.model.Calendar;
import org.mule.module.google.calendar.model.Event;

public class GetInstancesTestCases extends GoogleCalendarTestParent {

	@Before
	public void setUp() {
		try {
			addToMessageTestObject((Map<String, Object>) context.getBean("getInstances"));

			Calendar calendar = runFlowAndGetPayload("create-calendar");
			
			addToMessageTestObject("calendarRef", calendar);
			addToMessageTestObject("calendarId", calendar.getId());
			
			// Insert the event			
			Event returnedEvent = runFlowAndGetPayload("insert-event");
			addToMessageTestObject("event", returnedEvent);
			addToMessageTestObject("eventId", returnedEvent.getId());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})	
	@Test
	public void testGetInstances() {
		try {
			
			String eventId = getValueFromMessageTestObject("eventId");		
			List<Event> returnedEvent = runFlowAndGetPayload("get-instances");
			
			for (Event event : returnedEvent) {
				assertEquals(event.getId(), eventId);
			}
				
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	@After
	public void tearDown() {
		try {
			String calendarId = getValueFromMessageTestObject("calendarId");
			deleteCalendar(calendarId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
