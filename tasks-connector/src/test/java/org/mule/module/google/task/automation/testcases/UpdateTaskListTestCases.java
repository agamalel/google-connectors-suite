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

public class UpdateTaskListTestCases extends GoogleTaskTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		testObjects = (HashMap<String, Object>) context.getBean("updateTaskList");
		TaskList taskList = (TaskList) testObjects.get("taskListRef");
		String title = taskList.getTitle();
		MuleEvent insertTaskListResponse = null;
		try {
			insertTaskListResponse = lookupFlowConstruct("insert-task-list")
					.process(getTestEvent(testObjects));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		TaskList insertedTaskList = (TaskList) insertTaskListResponse.getMessage().getPayload();
		testObjects.put("taskListId", insertedTaskList.getId());
		testObjects.put("taskListRef",insertedTaskList);
		assertEquals("The inserted title should be " + title, title, insertedTaskList.getTitle());
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
	public void testUpdateTaskList() {
		TaskList taskList = (TaskList) testObjects.get("taskListRef");
		String updatedTitle = "the update title";
		taskList.setTitle(updatedTitle);
		
		
		MuleEvent updateTaskListResponse = null;
		MuleEvent getTaskListByIdResponse = null;
		try {
			updateTaskListResponse = lookupFlowConstruct("update-task-list").process(
					getTestEvent(testObjects));
			getTaskListByIdResponse = lookupFlowConstruct("get-task-list-by-id")
					.process(getTestEvent(testObjects));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		TaskList updatedTask = (TaskList) updateTaskListResponse.getMessage().getPayload();

		assertEquals(
				"The updated title should be " + updatedTitle,
				updatedTitle, updatedTask.getTitle());

		TaskList retrievedTaskList = (TaskList) getTaskListByIdResponse.getMessage()
				.getPayload();

		assertEquals(
				"The retrieved title should be " + updatedTitle,
				updatedTitle, retrievedTaskList.getTitle());
	}
}
