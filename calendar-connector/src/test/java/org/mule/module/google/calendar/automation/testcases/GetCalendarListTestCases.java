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
import org.mule.module.google.calendar.model.CalendarList;
import org.mule.modules.google.api.client.batch.BatchResponse;

public class GetCalendarListTestCases extends GoogleCalendarTestParent {

	protected List<Calendar> insertedCalendars = new ArrayList<Calendar>();
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getCalendarList");
			
			int numCalendars = (Integer) testObjects.get("numCalendars");
			
			// Create calendar instances
			List<Calendar> calendars = new ArrayList<Calendar>();
			for (int i = 0; i < numCalendars; i++) {
				calendars.add(getCalendar("This is a title"));
			}

			// Insert calendars and record their IDs
			BatchResponse<Calendar> response = insertCalendars(calendars);	
			assertTrue(response.getErrors() == null || response.getErrors().size() == 0);
			
			for (Calendar calendar : response.getSuccessful()) {
				insertedCalendars.add(calendar);
			}					
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, SanityTests.class})
	@Test
	public void testGetCalendarList() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-calendar-list");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<CalendarList> calendarList = (List<CalendarList>) response.getMessage().getPayload();
			
			for (Calendar insertedCalendar : insertedCalendars) {
				assertTrue(calendarExistsInList(calendarList, insertedCalendar));
			}
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}
	
	private boolean calendarExistsInList(List<CalendarList> list, Calendar toSearch) {
		for (CalendarList calendar : list) {
			if (calendar.getId().equals(toSearch.getId())) {
				return true;
			}
		}
		return false;
	}
	
	@After
	public void tearDown() {
		try {
			// Delete the calendars
			deleteCalendars(insertedCalendars);			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
