package org.mule.module.google.task.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.task.model.TaskList;

public class GetTaskListsTestCases extends GoogleTaskTestParent {
	
	private static final int NUMBER_OF_TASK_LISTS = 3;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		testObjects = (HashMap<String, Object>) context.getBean("getTaskLists");
		MessageProcessor insertTaskListFlow = lookupFlowConstruct("insert-task-list");
		try {
			for(int i = 0; i < NUMBER_OF_TASK_LISTS; i++) {
				insertTaskListFlow.process(getTestEvent(testObjects));
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		int numOfListsDeleted = deleteAllListsByTaskListTitle(((TaskList) testObjects.get("taskListRef")).getTitle());
		assertTrue(numOfListsDeleted == NUMBER_OF_TASK_LISTS);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetTaskLists() {
		MuleEvent getTaskListsResponse = null;
		try {
			getTaskListsResponse = lookupFlowConstruct("get-task-lists")
					.process(getTestEvent(testObjects));
			
			List<TaskList> taskLists = (List<TaskList>) getTaskListsResponse.getMessage().getPayload();
			String title = ((TaskList) testObjects.get("taskListRef")).getTitle();
		
			// construct a list of TaskList with the TaskList(s) inserted in setUp (i.e. excluding the default Google tasks list)
			List<TaskList> taskListsInserted = new ArrayList<TaskList>();
			for(TaskList task : taskLists) {
				if(task.getTitle().equals(title)) {
					taskListsInserted.add(task);
				}
			}
			assertEquals("There should be " + NUMBER_OF_TASK_LISTS + " task lists retrieved by get-task-lists with the title " + title, 
					NUMBER_OF_TASK_LISTS, taskListsInserted.size());
		} catch (Exception e1) {
			e1.printStackTrace();
			fail();
		}
	}
}
