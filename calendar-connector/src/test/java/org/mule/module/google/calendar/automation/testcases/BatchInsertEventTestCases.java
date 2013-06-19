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
	
	@Category({SmokeTests.class, SanityTests.class})
	@Test
	public void testBatchInsertEvent() {
		try {			
			// Get start and end time beans.
			EventDateTime eventStartTime = (EventDateTime) context.getBean("eventStartTime");
			EventDateTime eventEndTime = (EventDateTime) context.getBean("eventEndTime");			
			int numEvents = Integer.parseInt(testObjects.get("numEvents").toString());
			
			// Instantiate the events that we want to batch insert
			List<Event> events = new ArrayList<Event>();
			for (int i = 0; i < numEvents; i++) {
				Event event = getEvent("Test Event", eventStartTime, eventEndTime);
				events.add(event);
			}
			
			testObjects.put("calendarEventsRef", events);
			
			// Batch insert the events
			MessageProcessor flow = lookupFlowConstruct("batch-insert-event");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			// Assert that there are no errors
			BatchResponse<Event> batchResponse = (BatchResponse<Event>) response.getMessage().getPayload();
			assertTrue(batchResponse.getErrors() == null || batchResponse.getErrors().size() == 0);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
	