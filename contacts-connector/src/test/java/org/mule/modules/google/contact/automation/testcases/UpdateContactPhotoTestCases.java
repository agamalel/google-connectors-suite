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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.google.api.domain.BatchResult;
import org.mule.modules.google.contact.wrappers.GoogleContactEntry;

import com.google.gdata.data.contacts.ContactEntry;

public class UpdateContactPhotoTestCases extends GoogleContactsTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("updateContactPhoto");
			GoogleContactEntry contact = (GoogleContactEntry) testObjects.get("contact");
			
			/*
			 * The following will be changed once insert-contact is fixed
			 */
			
			List<BatchResult> result = insertContacts("myId", "myId", contact);
			BatchResult batchResult = result.get(0);
			
			ContactEntry entry = (ContactEntry) batchResult.getEntry();
			GoogleContactEntry wrapper = new GoogleContactEntry(entry);
			
			testObjects.put("insertedContact", wrapper);
			testObjects.put("contactId", getRealId(wrapper.getId()));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testUpdateContactPhoto() {
		try {
			String photoPath = (String) testObjects.get("photoPath");
			
			InputStream inRef = getClass().getClassLoader().getResourceAsStream(photoPath);
			testObjects.put("inRef", inRef);
			
			MessageProcessor flow = lookupFlowConstruct("update-contact-photo");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			fail("Figure out how to perform assertions for this test. Checking manually, this message processor does its job.");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			GoogleContactEntry contact = (GoogleContactEntry) testObjects.get("insertedContact");
			deleteContact(contact);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
