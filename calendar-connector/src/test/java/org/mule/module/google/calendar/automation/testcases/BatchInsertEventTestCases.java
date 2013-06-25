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

import java.util.ArrayList;
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
import org.mule.module.google.calendar.model.EventDateTime;
import org.mule.modules.google.api.client.batch.BatchResponse;

public class BatchInsertEventTestCases extends GoogleCalendarTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("batchInsertEvent");

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
	
	@After
	public void tearDown() {
		try {
			String calendarId = testObjects.get("calendarId").toString();
			deleteCalendar(calendarId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testBatchInsertEvent() {
		try {			
			// Get start and end time beans.
			EventDateTime eventStartTime = (EventDateTime) context.getBean("eventStartTime");
			EventDateTime eventEndTime = (EventDateTime) context.getBean("eventEndTime");			
			int numEvents = Integer.parseInt(testObjects.get("numEvents").toString());
			String calendarId = testObjects.get("calendarId").toString();
			
			// Instantiate the events that we want to batch insert
			List<Event> events = new ArrayList<Event>();
			for (int i = 0; i < numEvents; i++) {
				Event event = getEvent("Test Event", eventStartTime, eventEndTime);
				events.add(event);
			}
		
			// Batch insert the events
			BatchResponse<Event> batchResponse = insertEvents(calendarId, events);
			assertTrue(batchResponse.getErrors() == null || batchResponse.getErrors().size() == 0);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
	