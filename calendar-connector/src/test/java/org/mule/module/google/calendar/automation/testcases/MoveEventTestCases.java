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
			addToMessageTestObject((Map<String, Object>) context.getBean("moveEvent"));
			
			// Insert the source calendar and target calendar
			addToMessageTestObject("calendarRef", getValueFromMessageTestObject("sourceCalendarRef"));
			Calendar sourceCalendar = runFlowAndGetPayload("create-calendar");
			addToMessageTestObject("calendarRef", getValueFromMessageTestObject("targetCalendarRef"));
			Calendar targetCalendar = runFlowAndGetPayload("create-calendar");
			
			// Place updated calendars and their IDs into testObjects
			addToMessageTestObject("sourceCalendarRef", sourceCalendar);
			addToMessageTestObject("sourceCalendarId", sourceCalendar.getId());
			addToMessageTestObject("targetCalendarRef", targetCalendar);
			addToMessageTestObject("targetCalendarId", targetCalendar.getId());
			
			// Insert an event into the source calendar
			Event event = insertEvent(sourceCalendar, (Event) getValueFromMessageTestObject("event"));
			addToMessageTestObject("event", event);
			addToMessageTestObject("eventId", event.getId());
						
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({RegressionTests.class})	
	@Test
	public void testMoveEvent() {
		try {
			Calendar targetCalendar = getValueFromMessageTestObject("targetCalendarRef");
			
			// Move the event from the source calendar to the target calendar
			Event movedEvent = runFlowAndGetPayload("move-event");

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
			Calendar sourceCalendar = getValueFromMessageTestObject("sourceCalendarRef");
			Calendar targetCalendar = getValueFromMessageTestObject("targetCalendarRef");
			
			deleteCalendar(sourceCalendar);
			deleteCalendar(targetCalendar);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
