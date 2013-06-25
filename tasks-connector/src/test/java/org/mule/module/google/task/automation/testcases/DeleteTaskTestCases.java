package org.mule.module.google.task.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.module.google.task.model.Task;
import org.mule.module.google.task.model.TaskList;

public class DeleteTaskTestCases extends GoogleTaskTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		testObjects = (HashMap<String, Object>) context.getBean("deleteTask");
		MuleEvent insertTaskListResponse = null;
		MuleEvent insertTaskResponse = null;
		MuleEvent getTaskByIdResponse = null;
		try {
			insertTaskListResponse = lookupFlowConstruct("insert-task-list")
					.process(getTestEvent(testObjects));
			testObjects.put("taskListId", ((TaskList) insertTaskListResponse
					.getMessage().getPayload()).getId());
			insertTaskResponse = lookupFlowConstruct("insert-task").process(
					getTestEvent(testObjects));
			testObjects.put("taskId", ((Task) insertTaskResponse.getMessage()
					.getPayload()).getId());

			// assert task was inserted
			getTaskByIdResponse = lookupFlowConstruct("get-task-by-id")
					.process(getTestEvent(testObjects));
			Task task = (Task) getTaskByIdResponse.getMessage().getPayload();
			assertEquals(
					"Check that the task was inserted. Check its notes value.",
					((Task) testObjects.get("taskRef")).getNotes(),
					task.getNotes());
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

	@Test
	public void testDeleteTask() {
		MuleEvent getTaskByIdResponse = null;
		try {
			lookupFlowConstruct("delete-task").process(
					getTestEvent(testObjects));
			getTaskByIdResponse = lookupFlowConstruct("get-task-by-id").process(
					getTestEvent(testObjects));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		Task task = (Task) getTaskByIdResponse.getMessage().getPayload();
		assertTrue("The task should have been deleted", task.getDeleted());
	}
}
