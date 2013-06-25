package org.mule.module.google.calendar.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.google.calendar.model.Calendar;
import org.mule.modules.google.api.client.batch.BatchResponse;

public class BatchInsertCalendarTestCases extends GoogleCalendarTestParent {

	protected List<Calendar> insertedCalendars = new ArrayList<Calendar>();
	
	@Category({SmokeTests.class, SanityTests.class})
	@Test
	public void testBatchInsertCalendar() {
		try {
			testObjects = (Map<String, Object>) context.getBean("batchInsertCalendar");
			
			int numCalendars = (Integer) testObjects.get("numCalendars");
			
			// Create calendar instances
			List<Calendar> calendars = new ArrayList<Calendar>();
			for (int i = 0; i < numCalendars; i++) {
				calendars.add(getCalendar("This is a title"));
			}
	
			// Insert calendars
			BatchResponse<Calendar> response = insertCalendars(calendars);			
			
			// Assert that no errors exist in the response
			assertTrue(response.getErrors() == null || response.getErrors().size() == 0);
			assertTrue(response.getSuccessful().size() == numCalendars);
			
			// Add them to a global variable so that we can drop them in the tearDown method
			for (Calendar calendar : response.getSuccessful()) {
				insertedCalendars.add(calendar);
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
			// Delete the calendars
			deleteCalendars(insertedCalendars);			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
