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

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.google.contact.wrappers.GoogleContactGroupEntry;

public class CreateGroupTestCases extends GoogleContactsTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("createGroup");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testCreateGroup() {
		try {
			MessageProcessor flow = lookupFlowConstruct("create-group");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			GoogleContactGroupEntry createdGroup = (GoogleContactGroupEntry) response.getMessage().getPayload();
			
			assertTrue(StringUtils.isNotBlank(createdGroup.getId()));
			
			testObjects.put("groupId", createdGroup.getId());
			
			GoogleContactGroupEntry foundGroup = getGroupById(createdGroup.getId());
			assertTrue(createdGroup.equals(foundGroup));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String groupId = (String) testObjects.get("groupId");
			deleteGroupById(groupId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
