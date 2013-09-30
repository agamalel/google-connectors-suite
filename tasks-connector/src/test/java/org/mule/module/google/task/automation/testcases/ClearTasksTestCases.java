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

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.google.task.model.Task;
import org.mule.module.google.task.model.TaskList;
import org.mule.modules.google.api.datetime.DateTime;
import org.mule.modules.tests.ConnectorTestUtils;
import org.mule.streaming.ConsumerIterator;

public class ClearTasksTestCases extends GoogleTaskTestParent {

	private static final int NUMBER_OF_TASKS = 3;
	

	@Before
	public void setUp() throws Exception {
		loadTestRunMessage("clearTasks");
		Task uninsertedTask = getTestRunMessageValue("taskRef");
		
		upsertOnTestRunMessage("taskListId", ((TaskList) runFlowAndGetPayload("insert-task-list")).getId());
		for(int i = 0; i < NUMBER_OF_TASKS; i++) {
			upsertOnTestRunMessage("taskRef", uninsertedTask);
			Task insertedTask = runFlowAndGetPayload("insert-task");
			insertedTask.setCompleted(new DateTime(new Date()));
			insertedTask.setStatus("completed");
			upsertOnTestRunMessage("taskId", insertedTask.getId());
			upsertOnTestRunMessage("taskRef", insertedTask);
			runFlowAndGetPayload("update-task");

		}
	}

	@After
	public void tearDown() throws Exception {
		runFlowAndGetPayload("delete-task-list");
	}


	@Category({RegressionTests.class})
	@Test
	public void testClearTasks() {
		int tasksDoNotShowHiddenAmount = 0;
		int tasksShowHiddenAmount = 0;
		
		try {
			runFlowAndGetPayload("clear-tasks");
					
			ConsumerIterator<Task> tasksDoNotShowHidden = runFlowAndGetPayload("get-tasks");
			
			while (tasksDoNotShowHidden.hasNext()) {
				tasksDoNotShowHidden.next();
				tasksDoNotShowHiddenAmount++;
			}
			assertEquals("There should be  0 tasks retrieved by get-tasks with option showHidden=false " +
					"since clearing completed tasks makes them hidden",
				0, tasksDoNotShowHiddenAmount);

			upsertOnTestRunMessage("showHidden", "true");
			ConsumerIterator<Task> tasksShowHidden = runFlowAndGetPayload("get-tasks");
			while (tasksShowHidden.hasNext()) {
				tasksShowHidden.next();
				tasksShowHiddenAmount++;
			}
			assertEquals("There should be " + NUMBER_OF_TASKS + " tasks retreived by get-tasks with option showHidden=true",
				NUMBER_OF_TASKS, tasksShowHiddenAmount);

		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
		
	}
}
