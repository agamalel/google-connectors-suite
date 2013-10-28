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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.google.task.model.Task;
import org.mule.module.google.task.model.TaskList;
import org.mule.modules.tests.ConnectorTestUtils;
import org.mule.streaming.ConsumerIterator;

public class GetTasksTestCases extends GoogleTaskTestParent {
	
	private static final int NUMBER_OF_TASKS = 3;


	@Before
	public void setUp() throws Exception {
		loadTestRunMessage("getTasks");
		// insert a task list and NUMBER_OF_TASKS tasks inside the task list
		upsertOnTestRunMessage("taskListId", ((TaskList) runFlowAndGetPayload("insert-task-list")).getId());
		for(int i = 0; i < NUMBER_OF_TASKS; i++) {
			runFlowAndGetPayload("insert-task");
		}
	}
	
	@After
	public void tearDown() throws Exception {
			runFlowAndGetPayload("delete-task-list");
	}
	

	@Category({RegressionTests.class})
	@Test
	public void testGetTasks() {
		int tasksAmount = 0;
		
		try {
			ConsumerIterator<Task> tasks = runFlowAndGetPayload("get-tasks");
			
			while (tasks.hasNext()) {
				tasks.next();
				tasksAmount++;
			}
			assertEquals("There should be " + NUMBER_OF_TASKS + " tasks retrieved by get-tasks",
					NUMBER_OF_TASKS, tasksAmount);
			
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
		
	}
}
