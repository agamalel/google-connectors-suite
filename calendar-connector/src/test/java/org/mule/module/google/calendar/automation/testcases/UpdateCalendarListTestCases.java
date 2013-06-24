package org.mule.module.google.calendar.automation.testcases;

import static org.junit.Assert.assertEquals;
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

public class UpdateCalendarListTestCases extends GoogleCalendarTestParent{
	
	
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("updateCalendarList");
	
			Calendar calendar = insertCalendar((Calendar) testObjects.get("calendarRef"));
						
			testObjects.put("calendar", calendar);
			testObjects.put("id", calendar.getId());
			
			//Get Calendar List 
			MessageProcessor flow = lookupFlowConstruct("get-calendar-list-by-id");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			CalendarList returnedCalendarList = (CalendarList) response.getMessage().getPayload();
			
			testObjects.put("calendarList", returnedCalendarList);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	@Category({SmokeTests.class, SanityTests.class})
	@Test
	public void testUpdateCalendar() {
		try {
			String colorAfter = testObjects.get("colorAfter").toString();
			
			CalendarList returnedCalendarList = (CalendarList) testObjects.get("calendarList");
			
			returnedCalendarList.setColorId(colorAfter);
			testObjects.put("calendarListRef", returnedCalendarList);
						
			MessageProcessor flow = lookupFlowConstruct("update-calendar-list");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			CalendarList afterUpdate = (CalendarList) response.getMessage().getPayload();
			String afterColorId = afterUpdate.getColorId();
			assertEquals(afterColorId, colorAfter);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String calendarId = testObjects.get("id").toString();
			deleteCalendar(calendarId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	

}
