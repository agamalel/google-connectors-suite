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

public class InsertTaskTestCases extends GoogleTaskTestParent {


	@Before
	public void setUp() throws Exception {
		loadTestRunMessage("insertTask");
		TaskList taskList = runFlowAndGetPayload("insert-task-list");
		upsertOnTestRunMessage("taskListId", taskList.getId());
	}

	@After
	public void tearDown() throws Exception {
		runFlowAndGetPayload("delete-task-list");
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testInsertTask() {
		Task task;
		
		try {
			task = runFlowAndGetPayload("insert-task");
			assertEquals("Make sure that the inserted Task object has the right value for its notes", 
					((Task) getTestRunMessageValue("taskRef")).getNotes(), task.getNotes());
			
			upsertOnTestRunMessage("taskId", task.getId());
			
			task = runFlowAndGetPayload("get-task-by-id");
			assertEquals("Make sure that the Task obtained from get-task-by-id has the right value for its notes", 
					((Task) getTestRunMessageValue("taskRef")).getNotes(), task.getNotes());
			
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	
	}
}
