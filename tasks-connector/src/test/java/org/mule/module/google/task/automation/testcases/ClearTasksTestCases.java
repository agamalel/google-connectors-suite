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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.module.google.task.model.Task;
import org.mule.module.google.task.model.TaskList;
import org.mule.modules.google.api.datetime.DateTime;

public class ClearTasksTestCases extends GoogleTaskTestParent {

	private static final int NUMBER_OF_TASKS = 3;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		testObjects = (HashMap<String, Object>) context.getBean("clearTasks");
		Task uninsertedTask = (Task) testObjects.get("taskRef");
		
		MuleEvent insertTaskListResponse = null;
		MuleEvent insertTaskResponse = null;
		MuleEvent getTasksResponse = null;
		try {
			insertTaskListResponse = lookupFlowConstruct("insert-task-list")
					.process(getTestEvent(testObjects));
			testObjects.put("taskListId", ((TaskList) insertTaskListResponse
					.getMessage().getPayload()).getId());
			
			for(int i = 0; i < NUMBER_OF_TASKS; i++) {
				testObjects.put("taskRef", uninsertedTask);
				insertTaskResponse = lookupFlowConstruct("insert-task").process(getTestEvent(testObjects));
				Task insertedTask = (Task) insertTaskResponse.getMessage().getPayload();
				insertedTask.setCompleted(new DateTime(new Date()));
				insertedTask.setStatus("completed");
				testObjects.put("taskId", insertedTask.getId());
				testObjects.put("taskRef", insertedTask);
				MuleEvent updateTaskResponse = lookupFlowConstruct("update-task").process(getTestEvent(testObjects));
				
				Task updatedTask = (Task) updateTaskResponse.getMessage().getPayload();
				assertTrue("The task has been completed but because it has not been cleared yet, it should not be hidden",
						updatedTask.getHidden() == null);
			}
			
			getTasksResponse = lookupFlowConstruct("get-tasks")
					.process(getTestEvent(testObjects));
			List<Task> tasks = (List<Task>) getTasksResponse.getMessage().getPayload();
			assertEquals("There should be " + NUMBER_OF_TASKS + " completed tasks inserted in the task list",
				NUMBER_OF_TASKS, tasks.size());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@After
	public void tearDown() {
		try {
			lookupFlowConstruct("delete-task-list").process(
					getTestEvent(testObjects));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testClearTasks() {
		MuleEvent getTasksShowHiddenResponse = null;
		MuleEvent getTasksDoNotShowHiddenResponse = null;
		try {
			lookupFlowConstruct("clear-tasks").process(
					getTestEvent(testObjects));
			getTasksDoNotShowHiddenResponse = lookupFlowConstruct("get-tasks")
					.process(getTestEvent(testObjects));
			
			testObjects.put("showHidden", "true");
			getTasksShowHiddenResponse = lookupFlowConstruct("get-tasks")
					.process(getTestEvent(testObjects));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		List<Task> tasksDoNotShowHidden = (List<Task>) getTasksDoNotShowHiddenResponse.getMessage().getPayload();
		assertEquals("There should be  0 tasks retrieved by get-tasks with option showHidden=false " +
				"since clearing completed tasks makes them hidden",
			0, tasksDoNotShowHidden.size());

		List<Task> tasksShowHidden = (List<Task>) getTasksShowHiddenResponse.getMessage().getPayload();
		assertEquals("There should be " + NUMBER_OF_TASKS + " tasks retreived by get-tasks with option showHidden=true",
			NUMBER_OF_TASKS, tasksShowHidden.size());
	}
}
