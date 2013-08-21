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

public class GetEventsTestCases extends GoogleCalendarTestParent {

	@Before
	public void setUp() {
		try {
			addToMessageTestObject((Map<String, Object>) context.getBean("getEvents"));
			
			Calendar calendar = runFlowAndGetPayload("create-calendar");
			
			addToMessageTestObject("calendar", calendar);
			addToMessageTestObject("calendarId", calendar.getId());

			// Get the sample event
			Event sampleEvent = getValueFromMessageTestObject("sampleEvent");
			
			// Get start and end time beans.
			String eventTitle = sampleEvent.getSummary();
			EventDateTime eventStartTime = sampleEvent.getStart();
			EventDateTime eventEndTime = sampleEvent.getEnd();

			Integer numEvents = getValueFromMessageTestObject("numEvents");
			
			// Create the test events
			List<Event> events = new ArrayList<Event>();			
			for (int i = 0; i < numEvents; i++) {
				Event event = CalendarUtils.getEvent(eventTitle, eventStartTime, eventEndTime);
				events.add(event);
			}
			
			// Batch insert the events and store successfully created events in testObjects
			BatchResponse<Event> returnedEvents = insertEvents(calendar, events);
			
			addToMessageTestObject("events", returnedEvents.getSuccessful());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetEvents_AllEvents() {
		try {
			List<Event> insertedEvents = (List<Event>) getValueFromMessageTestObject("events");
						
			// Get the events		
			List<Event> returnedEvents = runFlowAndGetPayload("get-all-events");
			
			// Perform assertions on the list
			boolean listsSame = EqualsBuilder.reflectionEquals(insertedEvents, returnedEvents);
			assertTrue(listsSame);
			for (Event event : insertedEvents) {
				assertTrue(CalendarUtils.isEventInList(returnedEvents, event));
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetEvents_MaxResults() {
		try {

			Integer maxResults = getValueFromMessageTestObject("maxResults");
						
			// Get the events
			List<Event> returnedEvents = runFlowAndGetPayload("get-events");
			
			// Perform assertions on the list
			assertTrue(returnedEvents.size() == maxResults);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetEvents_ShowDeleted() {
		try {

			// We do not want any limit on the number of results we receive
			addToMessageTestObject("maxResults", Integer.MAX_VALUE);
			// We want to be able to retrieve deleted events
			addToMessageTestObject("showDeleted", true);
			
			String calendarId = getValueFromMessageTestObject("calendarId");
			
			// Delete all events which we created in the setUp() method
			List<Event> events = (List<Event>) getValueFromMessageTestObject("events");
			deleteEvents(calendarId, events);
						
			// Get the events			
			List<Event> returnedEvents = runFlowAndGetPayload("get-events");
			
			// Perform assertions on the list
			// Every event that was inserted should have been retrieved
			assertTrue(returnedEvents.size() == events.size());
			// Every event in the list should have been deleted, so check that the status is so
			for (Event event : returnedEvents) {
				assertTrue(event.getStatus().equals("cancelled"));	
				assertTrue(CalendarUtils.isEventInList(events, event));
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetEvents_UsingQuery() {
		try {
			
			Event sampleEvent = getValueFromMessageTestObject("sampleEvent");
			String eventTitle = sampleEvent.getSummary();
			List<Event> insertedEvents = (List<Event>) getValueFromMessageTestObject("events");
			
			// Get the events		
			List<Event> returnedEvents = runFlowAndGetPayload("get-events-using-query");
			
			// Since the query is a substring of the events' names, every created event should have been retrieved
			// Assert list sizes, assert returned event titles, and assert total equality on the inserted list of events and the retrieved list of events
			assertTrue(returnedEvents.size() == insertedEvents.size());
			for (Event event : returnedEvents) {
				assertTrue(event.getSummary().equals(eventTitle));
			}
			boolean listsSame = EqualsBuilder.reflectionEquals(insertedEvents, returnedEvents);
			assertTrue(listsSame);
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
