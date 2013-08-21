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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.calendar.automation.CalendarUtils;
import org.mule.module.google.calendar.model.Calendar;
import org.mule.module.google.calendar.model.Event;
import org.mule.module.google.calendar.model.EventDateTime;
import org.mule.modules.google.api.client.batch.BatchResponse;

public class BatchUpdateEventTestCases extends GoogleCalendarTestParent {

	@Before
	public void setUp() {
		try {
			addToMessageTestObject((Map<String, Object>) context.getBean("batchUpdateEvent"));
			
			Calendar calendar = runFlowAndGetPayload("create-calendar");
			addToMessageTestObject("calendar", calendar);
			addToMessageTestObject("calendarId", calendar.getId());

			EventDateTime eventTimeStart = getValueFromMessageTestObject("eventStart");
			EventDateTime eventTimeEnd = getValueFromMessageTestObject("eventEnd");
			String summaryBefore = getValueFromMessageTestObject("summaryBefore");
			Integer numEvents =getValueFromMessageTestObject("numEvents");
			
			List<Event> events = new ArrayList<Event>();
			for (int i = 0; i < numEvents; i++) {
				Event event = CalendarUtils.getEvent(summaryBefore, eventTimeStart, eventTimeEnd);
				events.add(event);
			}
			
			BatchResponse<Event> batchEvents = insertEvents(calendar, events);
			List<Event> successfulEvents = batchEvents.getSuccessful();
			
			addToMessageTestObject("events", successfulEvents);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({ RegressionTests.class})	
	@Test
	public void testBatchUpdateEvent() {
		try {
			
			String summaryAfter = getValueFromMessageTestObject("summaryAfter");
			List<Event> events = (List<Event>) getValueFromMessageTestObject("events");
			
			for (Event event : events) {
				event.setSummary(summaryAfter);
			}
			
			addToMessageTestObject("calendarEventsRef", events);
			BatchResponse<Event> returnedEvents = runFlowAndGetPayload("batch-update-event");
			assertTrue(returnedEvents.getErrors() == null || returnedEvents.getErrors().size() == 0);
			
			List<Event> successfulEvents = returnedEvents.getSuccessful();
			assertTrue(successfulEvents.size() == events.size());
			for (Event successfulEvent : successfulEvents) {
				assertTrue(CalendarUtils.isEventInList(events, successfulEvent));
			}
			
			assertTrue(EqualsBuilder.reflectionEquals(successfulEvents, events));
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@After
	public void tearDown() {
		try {
			Calendar calendar = getValueFromMessageTestObject("calendar");
			deleteCalendar(calendar);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}		
	}
	
}
