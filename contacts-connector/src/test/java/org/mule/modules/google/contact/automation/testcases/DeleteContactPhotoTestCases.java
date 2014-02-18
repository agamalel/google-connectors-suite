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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.google.api.domain.BatchResult;
import org.mule.modules.google.contact.wrappers.GoogleContactBaseEntity;
import org.mule.modules.google.contact.wrappers.GoogleContactEntry;

import com.google.gdata.data.contacts.ContactEntry;

public class DeleteContactPhotoTestCases extends GoogleContactsTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("deleteContactPhoto");
			
			GoogleContactEntry contact = (GoogleContactEntry) testObjects.get("contact");
			
			/*
			 * The following will be replaced when insert-contact is fixed			
			 */
			List<BatchResult> result = insertContacts("myBatchId", "myBatchId", contact);
			
			BatchResult batchResult = result.get(0);
			ContactEntry entry = (ContactEntry) batchResult.getEntry();
			GoogleContactEntry wrapper = new GoogleContactEntry(entry);

			String realId = getRealId(wrapper.getId());
			
			String photoPath = (String) testObjects.get("photoPath");
			updateContactPhoto(realId, photoPath);
			
			// We need to do this in order to retrieve the updated info
			// i.e. links to the uploaded photo
			GoogleContactEntry retrievedContact = getContact(realId);
			testObjects.put("insertedContact", retrievedContact);
			testObjects.put("id", realId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testDeleteContactPhoto() {
		try {
			GoogleContactEntry insertedContact = (GoogleContactEntry) testObjects.get("insertedContact");
			String id = (String) testObjects.get("id");
			
			testObjects.put("contactRef", insertedContact);
			
			MessageProcessor flow = lookupFlowConstruct("delete-contact-photo");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			GoogleContactEntry retrievedContact = getContact(id);
			
			ContactEntry wrappedEntity = GoogleContactBaseEntity.getWrappedEntity(ContactEntry.class, retrievedContact);
			String eTag = wrappedEntity.getContactPhotoLink().getEtag();
			
			// From documentation:
			// Note: If a contact does not have a photo, then the photo link element has no gd:etag attribute.
			assertTrue(eTag == null);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String id = (String) testObjects.get("id");
			GoogleContactEntry contact = getContact(id);
			
			deleteContact(contact);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
