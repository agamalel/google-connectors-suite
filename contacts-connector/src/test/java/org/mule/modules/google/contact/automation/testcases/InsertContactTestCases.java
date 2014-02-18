/**
 * Mule Google Contacts Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.google.contact.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.google.contact.wrappers.GoogleContactEntry;

public class InsertContactTestCases extends GoogleContactsTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("insertContact");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testInsertContact() {
		try {
			MessageProcessor flow = lookupFlowConstruct("insert-contact");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			GoogleContactEntry insertedContact = (GoogleContactEntry) response.getMessage().getPayload();
			assertNotNull(insertedContact);
			
			testObjects.put("insertedContact", insertedContact);
			
			GoogleContactEntry foundContact = getContact(insertedContact.getId());
			assertTrue(foundContact.equals(insertedContact));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			GoogleContactEntry insertedContact = (GoogleContactEntry) testObjects.get("insertedContact");
			deleteContact(insertedContact);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
