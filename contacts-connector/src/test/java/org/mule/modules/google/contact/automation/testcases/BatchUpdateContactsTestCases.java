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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.HttpURLConnection;
import java.util.ArrayList;
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

import com.google.gdata.data.batch.BatchStatus;
import com.google.gdata.data.contacts.ContactEntry;

public class BatchUpdateContactsTestCases extends GoogleContactsTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("batchUpdate");

			List<GoogleContactEntry> contacts = (List<GoogleContactEntry>) testObjects.get("entries");
			String operationId = (String) testObjects.get("operationId");
			String batchId = (String) testObjects.get("batchId");
			
			List<BatchResult> result = insertContacts(batchId, operationId, contacts);
			List<GoogleContactEntry> successfulEntries = new ArrayList<GoogleContactEntry>();
			
			for (BatchResult batchResult : result) {
				if (batchResult.getStatus().getCode() == HttpURLConnection.HTTP_CREATED) {
					ContactEntry contactEntry = (ContactEntry) batchResult.getEntry();
					GoogleContactEntry wrapper = new GoogleContactEntry(contactEntry);
					successfulEntries.add(wrapper);
				}
			}
			
			testObjects.put("successfulEntries", successfulEntries);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testBatchUpdateContacts() {
		try {
			List<GoogleContactEntry> successfulEntries = (List<GoogleContactEntry>) testObjects.get("successfulEntries");
			List<GoogleContactEntry> updatedEntries = (List<GoogleContactEntry>) testObjects.get("updatedEntries");
			
			assertEquals(successfulEntries.size(), updatedEntries.size());
			
			for (int i = 0; i < successfulEntries.size(); i++) {
				GoogleContactEntry insertedEntry = successfulEntries.get(0);
				GoogleContactEntry updatedEntry = updatedEntries.get(0);

				insertedEntry.setGivenName(updatedEntry.getGivenName());
				insertedEntry.setFamilyName(updatedEntry.getFamilyName());
				insertedEntry.setEmailAddresses(updatedEntry.getEmailAddresses());
			}
			
			testObjects.put("entriesRef", successfulEntries);
			
			MessageProcessor flow = lookupFlowConstruct("batch-update-contacts");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<BatchResult> result = (List<BatchResult>) response.getMessage().getPayload();
			for (BatchResult batchResult : result) {
				BatchStatus status = batchResult.getStatus();
				assertEquals(status.getCode(), HttpURLConnection.HTTP_OK);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			List<GoogleContactEntry> contacts = (List<GoogleContactEntry>) testObjects.get("successfulEntries");
			String operationId = (String) testObjects.get("operationId");
			String batchId = (String) testObjects.get("batchId");
			
			deleteContacts(batchId, operationId, contacts);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}