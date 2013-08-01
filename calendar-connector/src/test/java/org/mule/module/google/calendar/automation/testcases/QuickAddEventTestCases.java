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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.calendar.model.Calendar;
import org.mule.module.google.calendar.model.Event;

public class QuickAddEventTestCases extends GoogleCalendarTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("quickAddEvent");
			
			// Insert calendar and get reference to retrieved calendar
			Calendar calendar = insertCalendar((Calendar) testObjects.get("calendarRef"));
			
			// Replace old calendar instance with new instance
			testObjects.put("calendarRef", calendar);
			testObjects.put("calendarId", calendar.getId());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})	
	@Test
	public void testQuickAddEvent() {
		try {
			String text = testObjects.get("text").toString();
			
			// Quick add the event
			MessageProcessor flow = lookupFlowConstruct("quick-add-event");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			// Perform assertions on the event
			Event createdEvent = (Event) response.getMessage().getPayload();
			assertNotNull(createdEvent);
			assertTrue(createdEvent.getSummary().equals(text));
			assertTrue(createdEvent.getStatus().equals("confirmed"));
			
			testObjects.put("event", createdEvent);
			testObjects.put("eventId", createdEvent.getId());
			
			// Verify that the event was added on Google Calendars			
			flow = lookupFlowConstruct("get-event-by-id");
			response = flow.process(getTestEvent(testObjects));
			
			Event returnedEvent = (Event) response.getMessage().getPayload();
			
			// Assert that the created event and the returned are identical
			assertTrue(EqualsBuilder.reflectionEquals(createdEvent, returnedEvent));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			// Drop the calendar
			String calendarId = testObjects.get("calendarId").toString();
			deleteCalendar(calendarId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
}
