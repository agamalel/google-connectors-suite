package org.mule.module.google.calendar.automation.testcases;

import static org.junit.Assert.assertEquals;
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
import org.mule.module.google.calendar.model.CalendarList;

public class GetCalendarListByIdTestCases extends GoogleCalendarTestParent{

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getCalendarListById");

			// Create the calendar
			MessageProcessor flow = lookupFlowConstruct("create-calendar");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Calendar calendar = (Calendar) response.getMessage().getPayload();
			testObjects.put("calendar",calendar);
			testObjects.put("id", calendar.getId());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, SanityTests.class})
	@Test
	public void testGetCalendarById() {
		try {
			
			Calendar originalCalendar = (Calendar) testObjects.get("calendar");
			
			String createdCalendarId = testObjects.get("id").toString();
			
			MessageProcessor flow = lookupFlowConstruct("get-calendar-list-by-id");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			CalendarList returnedCalendarList = (CalendarList) response.getMessage().getPayload();

			assertTrue(returnedCalendarList != null);
			assertTrue(returnedCalendarList.getId().equals(createdCalendarId));
			assertEquals(returnedCalendarList.getSummary(), originalCalendar.getSummary());
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			// Delete the calendar
			MessageProcessor flow = lookupFlowConstruct("delete-calendar");
			MuleEvent response = flow.process(getTestEvent(testObjects));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
