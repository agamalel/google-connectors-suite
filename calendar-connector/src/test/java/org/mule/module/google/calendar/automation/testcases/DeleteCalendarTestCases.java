package org.mule.module.google.calendar.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.calendar.model.Calendar;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class DeleteCalendarTestCases extends GoogleCalendarTestParent {


	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("deleteCalendar");

			// Create the calendar
			MessageProcessor flow = lookupFlowConstruct("create-calendar");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Calendar calendar = (Calendar) response.getMessage().getPayload();
			testObjects.put("id", calendar.getId());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	@Test
	public void testDeleteCalendar() {
		try {
			// Delete the calendar
			MessageProcessor flow = lookupFlowConstruct("delete-calendar");
			MuleEvent response = flow.process(getTestEvent(testObjects));

		}
		catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
			
		// Get the calendar, should throw an exception
		try {
			MessageProcessor flow = lookupFlowConstruct("get-calendar-by-id");			
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
	
}
