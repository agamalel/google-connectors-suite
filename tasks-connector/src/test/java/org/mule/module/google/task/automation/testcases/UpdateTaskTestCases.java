package org.mule.module.google.task.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.module.google.task.model.Task;
import org.mule.module.google.task.model.TaskList;
import org.mule.modules.google.api.datetime.DateTime;

public class UpdateTaskTestCases extends GoogleTaskTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		testObjects = (HashMap<String, Object>) context.getBean("updateTask");
		MuleEvent insertTaskListResponse = null;
		MuleEvent insertTaskResponse = null;
		try {
			insertTaskListResponse = lookupFlowConstruct("insert-task-list")
					.process(getTestEvent(testObjects));
			testObjects.put("taskListId", ((TaskList) insertTaskListResponse
					.getMessage().getPayload()).getId());
			insertTaskResponse = lookupFlowConstruct("insert-task").process(
					getTestEvent(testObjects));
			testObjects.put("taskId", ((Task) insertTaskResponse.getMessage()
					.getPayload()).getId());
			Task task = (Task) insertTaskResponse.getMessage().getPayload();
			testObjects.put("taskRef", task);
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

	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testUpdateTask() {
		Task task = (Task) testObjects.get("taskRef");
		
		Calendar calendar = new GregorianCalendar(2008, 01, 01);
		Date date = calendar.getTime();
		task.setCompleted(new DateTime(date));
		// if you're updating time completed the status needs to be set to 'completed' otherwise you'll get an invalid value response
		String status = "completed";
		task.setStatus(status);

		MuleEvent updateTaskResponse = null;
		MuleEvent getTaskByIdResponse = null;
		try {
			updateTaskResponse = lookupFlowConstruct("update-task").process(
					getTestEvent(testObjects));
			getTaskByIdResponse = lookupFlowConstruct("get-task-by-id")
					.process(getTestEvent(testObjects));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		Task updatedTask = (Task) updateTaskResponse.getMessage().getPayload();

		assertEquals(
				"The task returned from the update operation should have its completed time updated",
				date.getTime(), updatedTask.getCompleted().getValue());
		assertEquals(
				"The task returned from the update operation should have its status be 'completed'",
				status, updatedTask.getStatus());

		Task retrievedTask = (Task) getTaskByIdResponse.getMessage()
				.getPayload();

		assertEquals(
				"The task retrieved from the get operation should have its completed time updated",
				date.getTime(), retrievedTask.getCompleted().getValue());
		assertEquals(
				"The task retrieved from the update operation should have its status be 'completed'",
				status, retrievedTask.getStatus());
	}
}
