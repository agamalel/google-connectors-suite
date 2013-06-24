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
import org.mule.module.google.calendar.model.CalendarList;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class DeleteCalendarListTestCases extends GoogleCalendarTestParent{
	
	
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("deleteCalendarList");
	
			Calendar calendar = insertCalendar((Calendar) testObjects.get("calendarRef"));
						
			testObjects.put("calendar", calendar);
			testObjects.put("id", calendar.getId());
			
			//Get Calendar List 
			MessageProcessor flow = lookupFlowConstruct("get-calendar-list-by-id");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			CalendarList returnedCalendarList = (CalendarList) response.getMessage().getPayload();
			
			testObjects.put("calendarList", returnedCalendarList);
			testObjects.put("color", returnedCalendarList.getColorId());

		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	@Category({SmokeTests.class, SanityTests.class})
	@Test
	public void testDeleteCalendarList() {
		try {
									
			MessageProcessor flow = lookupFlowConstruct("delete-calendar-list");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
				
		// Get the calendar list, should throw an exception
		try {
			MessageProcessor flow = lookupFlowConstruct("get-calendar-list-by-id");
			MuleEvent response = flow.process(getTestEvent(testObjects));
		}
		catch (Exception e) {
			if (e.getCause() instanceof GoogleJsonResponseException) {
				GoogleJsonResponseException googleException = (GoogleJsonResponseException) e.getCause();
				 // Not found
				assertTrue(googleException.getStatusCode() == 404);
				assertTrue(googleException.getStatusMessage().equals("Not Found"));
			}
			else fail();
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
