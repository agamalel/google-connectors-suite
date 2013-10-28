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

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.google.task.model.TaskList;
import org.mule.modules.tests.ConnectorTestUtils;
import org.mule.streaming.ConsumerIterator;

public class GetTaskListsTestCases extends GoogleTaskTestParent {
	
	private static final int NUMBER_OF_TASK_LISTS = 3;
	
	@Before
	public void setUp() throws Exception {
		loadTestRunMessage("getTaskLists");
		for(int i = 0; i < NUMBER_OF_TASK_LISTS; i++) {
			runFlowAndGetPayload("insert-task-list");
		}

	}
	
	@After
	public void tearDown() throws Exception {
		// All tasks lists which have the title taskListTitle will be deleted. 
		String taskListTitle = ((TaskList) getTestRunMessageValue("taskListRef")).getTitle();
	
		ConsumerIterator<TaskList> taskLists = runFlowAndGetPayload("get-task-lists");
		while (taskLists.hasNext()) {
			TaskList taskList = taskLists.next(); 
			if(taskListTitle.equals(taskList.getTitle())) {
				upsertOnTestRunMessage("taskListId", taskList.getId());
				runFlowAndGetPayload("delete-task-list");
			}
		}
	}
	

	@Category({RegressionTests.class})
	@Test
	public void testGetTaskLists() {
		try {
			ConsumerIterator<TaskList> taskLists = runFlowAndGetPayload("get-task-lists");
			String title = ((TaskList) getTestRunMessageValue("taskListRef")).getTitle();
		
			// construct a list of TaskList with the TaskList(s) inserted in setUp (i.e. excluding the default Google tasks list)
			List<TaskList> taskListsInserted = new ArrayList<TaskList>();
			while (taskLists.hasNext()) {
				TaskList taskList = taskLists.next(); 
				if(taskList.getTitle().equals(title)) {
					taskListsInserted.add(taskList);
				}
			}

			assertEquals("There should be " + NUMBER_OF_TASK_LISTS + " task lists retrieved by get-task-lists with the title " + title, 
					NUMBER_OF_TASK_LISTS, taskListsInserted.size());
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
}
