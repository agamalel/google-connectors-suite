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
			testObjects = (Map<String, Object>) context.getBean("getInstances");

			Calendar calendar = insertCalendar((Calendar) testObjects.get("calendarRef"));
			
			testObjects.put("calendarRef", calendar);
			testObjects.put("calendarId", calendar.getId());
			
			// Insert the event
			MessageProcessor flow = lookupFlowConstruct("insert-event");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Event returnedEvent = (Event) response.getMessage().getPayload();
			testObjects.put("event", returnedEvent);
			testObjects.put("eventId", returnedEvent.getId());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, SanityTests.class})	
	@Test
	public void testGetInstances() {
		try {
			
			String eventId = testObjects.get("eventId").toString();
			MessageProcessor flow = lookupFlowConstruct("get-instances");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<Event> returnedEvent = (List<Event>) response.getMessage().getPayload();
			
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
			String calendarId = testObjects.get("calendarId").toString();
			deleteCalendar(calendarId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
