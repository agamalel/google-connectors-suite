/**
 * Mule Google Calendars Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.google.calendar.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.calendar.model.Calendar;
import org.mule.module.google.calendar.model.Event;
import org.mule.module.google.calendar.model.EventDateTime;

public class ImportEventTestCases extends GoogleCalendarTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("importEvent");
			
			// Insert the calendar
			Calendar calendar = insertCalendar((Calendar) testObjects.get("calendarRef"));
			
			testObjects.put("calendar", calendar);
			testObjects.put("calendarId", calendar.getId());
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testImportEvent() {
		try {
			// Get calendar instance
			Calendar calendar = (Calendar) testObjects.get("calendar");
			// Insert the event so that we get ID, and iCalUID
			Event event = insertEvent(calendar, (Event) testObjects.get("event"));

			// Place it in testObjects so that we can import it back
			testObjects.put("calendarEventRef", event);
			
			// Re-import the event again
			MessageProcessor flow = lookupFlowConstruct("import-event");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			// Check that the returned event as it was imported is identical to the one which was placed the first time
			Event returnedEvent = (Event) response.getMessage().getPayload();
			assertTrue(EqualsBuilder.reflectionEquals(returnedEvent.getSummary(), event.getSummary()));
			assertTrue(EqualsBuilder.reflectionEquals(returnedEvent.getStart(), event.getStart()));
			assertTrue(EqualsBuilder.reflectionEquals(returnedEvent.getEnd(), event.getEnd()));
			assertTrue(EqualsBuilder.reflectionEquals(returnedEvent.getICalUID(), event.getICalUID()));
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
			Calendar calendar = (Calendar) testObjects.get("calendar");
			deleteCalendar(calendar);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
