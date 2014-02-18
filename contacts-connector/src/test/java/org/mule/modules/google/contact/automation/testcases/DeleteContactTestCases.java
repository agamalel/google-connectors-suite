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

import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.google.contact.wrappers.GoogleContactEntry;

import com.google.gdata.data.contacts.ContactEntry;

public class DeleteContactTestCases extends GoogleContactsTestParent {
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("deleteContact");
			
			GoogleContactEntry entry = (GoogleContactEntry) testObjects.get("contact");

			GoogleContactEntry insertedContact = insertContact(entry);
			testObjects.put("entry", insertedContact);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testDeleteContact() {
		try {
			GoogleContactEntry entry = (GoogleContactEntry) testObjects.get("entry");
			
			MessageProcessor flow = lookupFlowConstruct("delete-contact");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			GoogleContactEntry retrievedEntry = getContact(entry.getId());
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
