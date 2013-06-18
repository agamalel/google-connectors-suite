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

import org.junit.After;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;


public class AuthorizeTestCases extends GoogleCalendarTestParent {

	@Test
	public void testAuthorize() {
		try {
			MessageProcessor flow = lookupFlowConstruct("authorize");
			MuleEvent response = flow.process(getTestEvent(null));
			
			Object payload = response.getMessage().getPayload();
			assertTrue(payload != null);
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
//			MessageProcessor flow = lookupFlowConstruct("unauthorize");
//			MuleEvent response = flow.process(getTestEvent(null));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
