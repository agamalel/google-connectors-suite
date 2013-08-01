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
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.task.model.Task;
import org.mule.module.google.task.model.TaskList;

public class GetTasksTestCases extends GoogleTaskTestParent {
	
	private static final int NUMBER_OF_TASKS = 3;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		testObjects = (HashMap<String, Object>) context.getBean("getTasks");
		MuleEvent insertTaskListResponse = null;
		MessageProcessor insertTaskFlow = lookupFlowConstruct("insert-task");
		try {
			// insert a task list and NUMBER_OF_TASKS tasks inside the task list
			insertTaskListResponse = lookupFlowConstruct("insert-task-list").process(getTestEvent(testObjects));
			testObjects.put("taskListId", ((TaskList) insertTaskListResponse.getMessage().getPayload()).getId());
			for(int i = 0; i < NUMBER_OF_TASKS; i++) {
				insertTaskFlow.process(getTestEvent(testObjects));
			}
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
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetTasks() {
		MuleEvent getTasksResponse = null;
		try {
			getTasksResponse = lookupFlowConstruct("get-tasks")
					.process(getTestEvent(testObjects));
		} catch (Exception e1) {
			e1.printStackTrace();
			fail();
		}

		List<Task> tasks = (List<Task>) getTasksResponse.getMessage().getPayload();
		assertEquals("There should be " + NUMBER_OF_TASKS + " tasks retrieved by get-tasks",
				NUMBER_OF_TASKS, tasks.size());
	}
}
