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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.calendar.model.Calendar;
import org.mule.modules.google.api.client.batch.BatchResponse;

public class BatchUpdateCalendarTestCases extends GoogleCalendarTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("batchUpdateCalendar");
			
			int numCalendars =(Integer) testObjects.get("numCalendars");
			String summaryBefore = testObjects.get("summaryBefore").toString();
			
			List<Calendar> calendars = new ArrayList<Calendar>();
			for (int i = 0; i < numCalendars; i++) {
				Calendar calendar = getCalendar(summaryBefore);
				calendars.add(calendar);
			}
			
			BatchResponse<Calendar> calendarBatchResponse = insertCalendars(calendars);
			List<Calendar> insertedCalendars = calendarBatchResponse.getSuccessful();
			
			testObjects.put("calendars", insertedCalendars);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, SanityTests.class})	
	@Test
	public void testBatchUpdateCalendar() {
		try {
			List<Calendar> calendars = (List<Calendar>) testObjects.get("calendars");
			String summaryAfter = testObjects.get("summaryAfter").toString();
			
			for (Calendar calendar : calendars) {
				calendar.setSummary(summaryAfter);
			}
			
			testObjects.put("calendarsRef", calendars);
			
			MessageProcessor flow = lookupFlowConstruct("batch-update-calendar");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			BatchResponse<Calendar> updatedCalendars = (BatchResponse<Calendar>) response.getMessage().getPayload();
			assertTrue(updatedCalendars.getErrors() == null || updatedCalendars.getErrors().size() == 0);

			List<Calendar> successful = updatedCalendars.getSuccessful();
			
			for (Calendar cal : successful) {
				cal.getSummary().equals(summaryAfter);
			}
			
			assertTrue(EqualsBuilder.reflectionEquals(successful, calendars));
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			List<Calendar> calendars = (List<Calendar>) testObjects.get("calendarsRef");
			deleteCalendars(calendars);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}		
	}
}
