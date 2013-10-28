/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.google.task.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.google.task.model.Task;
import org.mule.module.google.task.model.TaskList;
import org.mule.modules.tests.ConnectorTestUtils;

public class MoveTaskTestCases extends GoogleTaskTestParent {
	

	@Before
	public void setUp() throws Exception {	
		loadTestRunMessage("moveTask");
		TaskList taskList = runFlowAndGetPayload("insert-task-list");
		
		upsertOnTestRunMessage("taskList", taskList);
		upsertOnTestRunMessage("taskListId", taskList.getId());
		
		Task firstTask = insertTask((Task) getTestRunMessageValue("firstTask"), taskList);
		Task secondTask = insertTask((Task) getTestRunMessageValue("secondTask"), taskList);
		
		upsertOnTestRunMessage("firstTask", firstTask);
		upsertOnTestRunMessage("firstTaskId", firstTask.getId());
		upsertOnTestRunMessage("secondTask", secondTask);
		upsertOnTestRunMessage("secondTaskId", secondTask.getId());
	}
	
	@After
	public void tearDown() throws Exception {
		runFlowAndGetPayload("delete-task-list");
	}

	@Category({RegressionTests.class})
	@Test
	public void testMoveTask_Parent() {
		try {
			Task firstTask = (Task) getTestRunMessageValue("firstTask");
			Task secondTask = (Task) getTestRunMessageValue("secondTask");
			
			upsertOnTestRunMessage("parentId", firstTask.getId());
			upsertOnTestRunMessage("taskId", secondTask.getId());

			Task movedTask = runFlowAndGetPayload("move-task-parent");
			assertTrue(movedTask.getParent().equals(firstTask.getId()));
			
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testMoveTask_Reorder() {
		try {	
		
			Task firstTask = (Task) getTestRunMessageValue("firstTask");
			Task secondTask = (Task) getTestRunMessageValue("secondTask");
			
			// Check that the first task comes after the second task
			// Tasks which are inserted first have a larger "position" value (lexicographically)
			// than those which are inserted after.
			assertTrue(firstTask.getPosition().compareTo(secondTask.getPosition()) == 1);
			
			upsertOnTestRunMessage("previousId", firstTask.getId());
			upsertOnTestRunMessage("taskId", secondTask.getId());

			Task movedTask = runFlowAndGetPayload("move-task-reorder");
			assertTrue(movedTask.getPosition().compareTo(secondTask.getPosition()) == 1);
			
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}

	}
}
