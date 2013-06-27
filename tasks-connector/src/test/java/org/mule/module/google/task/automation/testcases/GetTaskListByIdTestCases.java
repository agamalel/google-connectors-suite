/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.google.task.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.module.google.task.model.TaskList;

public class GetTaskListByIdTestCases extends GoogleTaskTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		testObjects = (HashMap<String, Object>) context.getBean("getTaskListById");
		MuleEvent insertTaskListResponse = null;
		try {
			insertTaskListResponse = lookupFlowConstruct("insert-task-list").process(getTestEvent(testObjects));
			testObjects.put("taskListId", ((TaskList) insertTaskListResponse.getMessage().getPayload()).getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			lookupFlowConstruct("delete-task-list").process(getTestEvent(testObjects));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetTaskListById() {
		MuleEvent getTaskListByIdResponse = null;
		try {
			getTaskListByIdResponse = lookupFlowConstruct("get-task-list-by-id")
					.process(getTestEvent(testObjects));
			assertEquals("get-task-list-by-id should return the task inserted in the setUp",
					((TaskList) testObjects.get("taskListRef")).getTitle(),
					((TaskList) getTaskListByIdResponse.getMessage()
							.getPayload()).getTitle());
		} catch (Exception e1) {
			e1.printStackTrace();
			fail();
		}
	}
}
