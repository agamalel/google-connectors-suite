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

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.google.task.model.TaskList;

public class DeleteTaskListTestCases extends GoogleTaskTestParent {


	@Before
	public void setUp() throws Exception {
		loadTestRunMessage("deleteTaskList");

		upsertOnTestRunMessage("taskListId", ((TaskList) runFlowAndGetPayload("insert-task-list")).getId());
		assertEquals("After inserting the task list, it should exist",
				((TaskList) getTestRunMessageValue("taskListRef")).getTitle(),
				((TaskList) runFlowAndGetPayload("get-task-list-by-id")).getTitle());

	}

	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testDeleteTaskList() {
		try {
			runFlowAndGetPayload("delete-task-list");
			runFlowAndGetPayload("get-task-list-by-id");
					
			fail("get-task-list-by-id should throw an exception: 404 Not Found. This is because the task list was deleted.");
		} catch (Exception e) {
			assertTrue(
					"The exception should be a 404 Not Found since the task list was deleted.",
					e.getCause().getMessage().contains("404 Not Found"));
		}
	}
}
