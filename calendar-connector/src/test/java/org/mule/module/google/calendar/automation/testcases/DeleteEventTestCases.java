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
			testObjects = (Map<String, Object>) context.getBean("deleteEvent");
			
			// Insert calendar and get reference to retrieved calendar
			Calendar calendar = insertCalendar((Calendar) testObjects.get("calendarRef"));
			
			// Replace old calendar instance with new instance
			testObjects.put("calendarRef", calendar);
			testObjects.put("calendarId", calendar.getId());
			
			MessageProcessor flow = lookupFlowConstruct("insert-event");
			MuleEvent event = flow.process(getTestEvent(testObjects));

			// Place the returned event and its ID into testObjects for later access
			Event returnedEvent = (Event) event.getMessage().getPayload();
			testObjects.put("event", returnedEvent);
			testObjects.put("eventId", event.getId());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, SanityTests.class})	
	@Test
	public void testDeleteEvent() {
		try {			
			// Delete the event
			MessageProcessor flow = lookupFlowConstruct("delete-event");
			MuleEvent event = flow.process(getTestEvent(testObjects));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		try {
			// Try and look for the event after deleting it
			MessageProcessor flow = lookupFlowConstruct("get-event-by-id");
			MuleEvent event = flow.process(getTestEvent(testObjects));
			
			// Fail the test if it reaches here, it should throw an exception
			fail();
		}
		catch (Exception e) {
			if (e.getCause() instanceof GoogleJsonResponseException) {
				// Catch the exception thrown by Google (event not found)
				GoogleJsonResponseException googleException = (GoogleJsonResponseException) e.getCause();
				assertTrue(googleException.getStatusCode() == 404); // Not found
				assertTrue(googleException.getStatusMessage().equals("Not Found"));
			}
			else fail();
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
}
