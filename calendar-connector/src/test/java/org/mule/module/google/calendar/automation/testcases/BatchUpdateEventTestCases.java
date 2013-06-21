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
import org.mule.module.google.calendar.model.Calendar;
import org.mule.module.google.calendar.model.Event;
import org.mule.module.google.calendar.model.EventDateTime;
import org.mule.modules.google.api.client.batch.BatchResponse;

public class BatchUpdateEventTestCases extends GoogleCalendarTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("batchUpdateEvent");
			
			Calendar calendar = insertCalendar((Calendar) testObjects.get("calendarRef"));
			testObjects.put("calendar", calendar);
			testObjects.put("calendarId", calendar.getId());

			EventDateTime eventTimeStart = (EventDateTime) testObjects.get("eventStart");
			EventDateTime eventTimeEnd = (EventDateTime) testObjects.get("eventEnd");
			String summaryBefore = testObjects.get("summaryBefore").toString();
			int numEvents =(Integer) testObjects.get("numEvents");
			
			List<Event> events = new ArrayList<Event>();
			for (int i = 0; i < numEvents; i++) {
				Event event = getEvent(summaryBefore, eventTimeStart, eventTimeEnd);
				events.add(event);
			}
			
			BatchResponse<Event> batchEvents = insertEvents(calendar, events);
			List<Event> successfulEvents = batchEvents.getSuccessful();
			
			testObjects.put("events", successfulEvents);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, SanityTests.class})	
	@Test
	public void testBatchUpdateEvent() {
		try {
			
			String summaryAfter = testObjects.get("summaryAfter").toString();
			List<Event> events = (List<Event>) testObjects.get("events");
			
			for (Event event : events) {
				event.setSummary(summaryAfter);
			}
			
			testObjects.put("calendarEventsRef", events);
			
			MessageProcessor flow = lookupFlowConstruct("batch-update-event");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			BatchResponse<Event> returnedEvents = (BatchResponse<Event>) response.getMessage().getPayload();
			assertTrue(returnedEvents.getErrors() == null || returnedEvents.getErrors().size() == 0);
			
			List<Event> successfulEvents = returnedEvents.getSuccessful();
			assertTrue(successfulEvents.size() == events.size());
			for (Event successfulEvent : successfulEvents) {
				assertTrue(existsInList(events, successfulEvent));
			}
			
			assertTrue(EqualsBuilder.reflectionEquals(successfulEvents, events));
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	private boolean existsInList(List<Event> events, Event event) {
		return existsInList(events, event.getId());
	}
	
	private boolean existsInList(List<Event> events, String id) {
		for (Event event : events) {
			if (event.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	@After
	public void tearDown() {
		try {
			Calendar calendar = (Calendar) testObjects.get("calendar");
			deleteCalendar(calendar);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}		
	}
	
}
