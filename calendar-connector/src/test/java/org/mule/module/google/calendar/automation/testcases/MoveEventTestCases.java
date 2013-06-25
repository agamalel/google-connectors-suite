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

public class MoveEventTestCases extends GoogleCalendarTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("moveEvent");
			
			// Insert the source calendar and target calendar
			Calendar sourceCalendar = insertCalendar((Calendar) testObjects.get("sourceCalendarRef"));
			Calendar targetCalendar = insertCalendar((Calendar) testObjects.get("targetCalendarRef"));
			
			// Place updated calendars and their IDs into testObjects
			testObjects.put("sourceCalendarRef", sourceCalendar);
			testObjects.put("sourceCalendarId", sourceCalendar.getId());
			testObjects.put("targetCalendarRef", targetCalendar);
			testObjects.put("targetCalendarId", targetCalendar.getId());
			
			// Insert an event into the source calendar
			Event event = insertEvent(sourceCalendar, (Event) testObjects.get("event"));
			testObjects.put("event", event);
			testObjects.put("eventId", event.getId());
						
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({SmokeTests.class, SanityTests.class})	
	@Test
	public void testMoveEvent() {
		try {
			Calendar targetCalendar = (Calendar) testObjects.get("targetCalendarRef");
			
			// Move the event from the source calendar to the target calendar
			MessageProcessor flow = lookupFlowConstruct("move-event");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Event movedEvent = (Event) response.getMessage().getPayload();

			// Perform assertions on the returned event
			assertTrue(movedEvent.getStatus().equals("cancelled")); // Default return status when moving an event
			assertTrue(movedEvent.getOrganizer().getDisplayName().equals(targetCalendar.getSummary()));
			assertTrue(movedEvent.getOrganizer().getEmail().equals(targetCalendar.getId())); 						
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
			Calendar sourceCalendar = (Calendar) testObjects.get("sourceCalendarRef");
			Calendar targetCalendar = (Calendar) testObjects.get("targetCalendarRef");
			
			deleteCalendar(sourceCalendar);
			deleteCalendar(targetCalendar);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
