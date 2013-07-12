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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.calendar.automation.CalendarUtils;
import org.mule.module.google.calendar.model.AclRule;
import org.mule.module.google.calendar.model.Calendar;

public class GetAllAclRulesTestCases extends GoogleCalendarTestParent {

	protected List<AclRule> insertedAclRules = new ArrayList<AclRule>();
		
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getAllAclRules");
			
			// Insert calendar and get reference to retrieved calendar
			Calendar calendar = insertCalendar((Calendar) testObjects.get("calendarRef"));
			
			// Replace old calendar instance with new instance
			testObjects.put("calendarRef", calendar);
			testObjects.put("calendarId", calendar.getId());

			MessageProcessor flow = lookupFlowConstruct("insert-acl-rule");
			List<String> scopes = (List<String>) testObjects.get("scopes");
			
			// Insert the different scopes
			for (String scope : scopes) {
				testObjects.put("scope", scope);
				MuleEvent response = flow.process(getTestEvent(testObjects));
				
				AclRule aclRule = (AclRule) response.getMessage().getPayload();
				insertedAclRules.add(aclRule);
			}
				
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetAllAclRules() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-all-acl-rules");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<AclRule> aclRuleList = (List<AclRule>) response.getMessage().getPayload();
			
			for (AclRule insertedAclRule : insertedAclRules) {
				assertTrue(CalendarUtils.isAclRuleInList(aclRuleList, insertedAclRule));
			}
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}	
	
	@After
	public void tearDown() {
		try {
			String calendarId = testObjects.get("calendarId").toString();
			deleteCalendar(calendarId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	
}
