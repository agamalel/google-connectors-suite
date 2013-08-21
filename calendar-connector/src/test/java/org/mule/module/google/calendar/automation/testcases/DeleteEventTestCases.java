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
import org.mule.module.google.calendar.model.Event;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class DeleteEventTestCases extends GoogleCalendarTestParent {

	@Before
	public void setUp() {
		try {
			addToMessageTestObject((Map<String, Object>) context.getBean("deleteEvent"));
			
			// Insert calendar and get reference to retrieved calendar
			Calendar calendar = runFlowAndGetPayload("create-calendar");
			
			// Replace old calendar instance with new instance
			addToMessageTestObject("calendarRef", calendar);
			addToMessageTestObject("calendarId", calendar.getId());

			// Place the returned event and its ID into testObjects for later access
			Event returnedEvent = runFlowAndGetPayload("insert-event");
			addToMessageTestObject("event", returnedEvent);
			addToMessageTestObject("eventId", returnedEvent.getId());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})	
	@Test
	public void testDeleteEvent() {
		try {			
			// Delete the event
			runFlowAndGetPayload("delete-event");
			// Try and look for the event after cancelling it	
			Event returnedEvent = runFlowAndGetPayload("get-event-by-id");
			assertTrue(returnedEvent.getStatus().equals("cancelled"));
			
		}
		catch (Exception e) {
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
