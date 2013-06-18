package org.mule.module.google.calendar.automation.testcases;

import static org.junit.Assert.assertNotNull;
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

public class InsertEventTestCases extends GoogleCalendarTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("insertEvent");
			
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
	
	@Category({SmokeTests.class, SanityTests.class})	
	@Test
	public void testInsertEvent() {
		try {
			MessageProcessor flow = lookupFlowConstruct("insert-event");
			MuleEvent event = flow.process(getTestEvent(testObjects));
			
			Event returnedEvent = (Event) event.getMessage().getPayload();
			assertNotNull(returnedEvent);
		
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
