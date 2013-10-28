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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.google.task.model.Task;
import org.mule.module.google.task.model.TaskList;
import org.mule.modules.google.api.datetime.DateTime;
import org.mule.modules.tests.ConnectorTestUtils;

public class UpdateTaskTestCases extends GoogleTaskTestParent {


	@Before
	public void setUp() throws Exception {
		loadTestRunMessage("updateTask");			
		upsertOnTestRunMessage("taskListId", ((TaskList) runFlowAndGetPayload("insert-task-list")).getId());
		Task task = runFlowAndGetPayload("insert-task");
		upsertOnTestRunMessage("taskId", task.getId());
		upsertOnTestRunMessage("taskRef", task);
	}

	@After
	public void tearDown() throws Exception {
		runFlowAndGetPayload("delete-task-list");
	}

	@Category({RegressionTests.class})
	@Test
	public void testUpdateTask() {
		Task task = (Task) getTestRunMessageValue("taskRef");
		
		Calendar calendar = new GregorianCalendar(2008, 01, 01);
		Date date = calendar.getTime();
		task.setCompleted(new DateTime(date));
		// if you're updating time completed the status needs to be set to 'completed' otherwise you'll get an invalid value response
		String status = "completed";
		task.setStatus(status);
		
		try {
			Task updatedTask = runFlowAndGetPayload("update-task");
	
			assertEquals(
					"The task returned from the update operation should have its completed time updated",
					date.getTime(), updatedTask.getCompleted().getValue());
			assertEquals(
					"The task returned from the update operation should have its status be 'completed'",
					status, updatedTask.getStatus());
	
			Task retrievedTask = runFlowAndGetPayload("get-task-by-id");
	
			assertEquals(
					"The task retrieved from the get operation should have its completed time updated",
					date.getTime(), retrievedTask.getCompleted().getValue());
			assertEquals(
					"The task retrieved from the update operation should have its status be 'completed'",
					status, retrievedTask.getStatus());
		
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
		
	}
}
