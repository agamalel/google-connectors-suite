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
import org.mule.module.google.task.model.TaskList;
import org.mule.modules.tests.ConnectorTestUtils;

public class UpdateTaskListTestCases extends GoogleTaskTestParent {


	@Before
	public void setUp() throws Exception {
		loadTestRunMessage("updateTaskList");
		TaskList insertedTaskList = runFlowAndGetPayload("insert-task-list");
		upsertOnTestRunMessage("taskListId", insertedTaskList.getId());
		upsertOnTestRunMessage("taskListRef",insertedTaskList);
		
	}

	@After
	public void tearDown() throws Exception {
		runFlowAndGetPayload("delete-task-list");
	}

	@Category({RegressionTests.class})
	@Test
	public void testUpdateTaskList() {
		
		try {
			
			TaskList taskList = (TaskList) getTestRunMessageValue("taskListRef");
			String updatedTitle = "the update title";
			taskList.setTitle(updatedTitle);

			TaskList updatedTask = runFlowAndGetPayload("update-task-list");
			assertEquals(
					"The updated title should be " + updatedTitle,
					updatedTitle, updatedTask.getTitle());
	
			TaskList retrievedTaskList = runFlowAndGetPayload("get-task-list-by-id");
			assertEquals(
					"The retrieved title should be " + updatedTitle,
					updatedTitle, retrievedTaskList.getTitle());
		
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
		
	}
}
