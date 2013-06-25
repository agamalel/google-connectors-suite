package org.mule.module.google.task.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.module.google.task.model.TaskList;

public class DeleteTaskListTestCases extends GoogleTaskTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		testObjects = (HashMap<String, Object>) context
				.getBean("deleteTaskList");
		MuleEvent insertTaskListResponse = null;
		MuleEvent getTaskListByIdResponse = null;
		try {
			insertTaskListResponse = lookupFlowConstruct("insert-task-list")
					.process(getTestEvent(testObjects));
			testObjects.put("taskListId", ((TaskList) insertTaskListResponse
					.getMessage().getPayload()).getId());
			getTaskListByIdResponse = lookupFlowConstruct("get-task-list-by-id")
					.process(getTestEvent(testObjects));
			assertEquals("After inserting the task list, it should exist",
					((TaskList) testObjects.get("taskListRef")).getTitle(),
					((TaskList) getTaskListByIdResponse.getMessage()
							.getPayload()).getTitle());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteTaskList() {
		try {
			lookupFlowConstruct("delete-task-list").process(
					getTestEvent(testObjects));
			lookupFlowConstruct("get-task-list-by-id")
					.process(getTestEvent(testObjects));
			fail("get-task-list-by-id should throw an exception: 404 Not Found. This is because the task list was deleted.");
		} catch (Exception e) {
			assertTrue(
					"The exception should be a 404 Not Found since the task list was deleted.",
					e.getCause().getMessage().contains("404 Not Found"));
		}
	}
}
