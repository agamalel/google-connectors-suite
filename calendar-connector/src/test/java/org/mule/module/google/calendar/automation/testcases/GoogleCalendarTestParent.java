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

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.mule.api.MuleEvent;
import org.mule.api.config.MuleProperties;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.store.ObjectStore;
import org.mule.api.store.ObjectStoreException;
import org.mule.module.google.calendar.model.Calendar;
import org.mule.module.google.calendar.oauth.GoogleCalendarConnectorOAuthState;
import org.mule.modules.google.api.client.batch.BatchResponse;
import org.mule.tck.junit4.FunctionalTestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GoogleCalendarTestParent extends FunctionalTestCase {

	// Set global timeout of tests to 10minutes
    @Rule
    public Timeout globalTimeout = new Timeout(600000);
    
	protected static final String[] SPRING_CONFIG_FILES = new String[] { "AutomationSpringBeans.xml","HelperSpringBeans.xml" };
	protected static ApplicationContext context;
	protected Map<String, Object> testObjects;

	@Override
	protected String getConfigResources() {
		return "automation-test-flows.xml";
	}

	protected MessageProcessor lookupFlowConstruct(String name) {
		return (MessageProcessor) muleContext.getRegistry()
				.lookupFlowConstruct(name);
	}
	
	@Before
	public void init() throws ObjectStoreException {
		ObjectStore objectStore = muleContext.getRegistry().lookupObject(MuleProperties.DEFAULT_USER_OBJECT_STORE_NAME);
		objectStore.store("accessTokenId", (GoogleCalendarConnectorOAuthState)context.getBean("connectorOAuthState"));
	}

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILES);
	}
	
	/*
	 * Helper methods below
	 */
	
	protected Calendar insertCalendar(Calendar calendar) throws Exception {
		testObjects.put("calendarRef", calendar);
		MessageProcessor flow = lookupFlowConstruct("create-calendar");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (Calendar) response.getMessage().getPayload();
	}
	
	protected BatchResponse<Calendar> insertCalendars(List<Calendar> calendars) throws Exception {
		testObjects.put("calendarsRef", calendars);
		MessageProcessor flow = lookupFlowConstruct("batch-insert-calendar");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (BatchResponse<Calendar>) response.getMessage().getPayload();
	}
	
	protected void deleteCalendar(Calendar calendar) throws Exception {
		deleteCalendar(calendar.getId());
	}
	
	protected void deleteCalendar(String id) throws Exception {
		testObjects.put("id", id);
		MessageProcessor flow = lookupFlowConstruct("delete-calendar");
		MuleEvent response = flow.process(getTestEvent(testObjects));
	}
	
	protected void deleteCalendars(List<Calendar> calendars) throws Exception {
		testObjects.put("calendarsRef", calendars);
		MessageProcessor flow = lookupFlowConstruct("batch-delete-calendar");
		MuleEvent response = flow.process(getTestEvent(testObjects));
	}
	
	protected Calendar getCalendar(String summary) {
		Calendar calendar = new Calendar();
		calendar.setSummary(summary);
		return calendar;
	}
	
}
