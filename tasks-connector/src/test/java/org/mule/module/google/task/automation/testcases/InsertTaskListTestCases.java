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

public class InsertTaskListTestCases extends GoogleTaskTestParent {


	@Before
	public void setUp() throws Exception {
		loadTestRunMessage("insertTaskList");
	}
	
	@After
	public void tearDown() throws Exception {
			runFlowAndGetPayload("delete-task-list");

	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testInsertTaskList() {
		TaskList taskList;
		
		try {
			
			taskList = runFlowAndGetPayload("insert-task-list");
			assertEquals("The title of the TaskList returned by insert-task-list should be equal to the title of the TaskList inserted",
					((TaskList) getTestRunMessageValue("taskListRef")).getTitle(), taskList.getTitle());
			
			upsertOnTestRunMessage("taskListId", taskList.getId());
			
			taskList = runFlowAndGetPayload("get-task-list-by-id");
			assertEquals("The title of the TaskList returned by get-task-list-by-id should be equal to the title of the TaskList inserted",
					((TaskList) getTestRunMessageValue("taskListRef")).getTitle(), taskList.getTitle());
			
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}

		
	}
}
