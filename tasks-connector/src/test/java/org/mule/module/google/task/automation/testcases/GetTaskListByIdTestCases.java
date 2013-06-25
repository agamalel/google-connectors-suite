package org.mule.module.google.task.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.module.google.task.model.TaskList;

public class GetTaskListByIdTestCases extends GoogleTaskTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		testObjects = (HashMap<String, Object>) context.getBean("getTaskListById");
		MuleEvent insertTaskListResponse = null;
		try {
			insertTaskListResponse = lookupFlowConstruct("insert-task-list").process(getTestEvent(testObjects));
			testObjects.put("taskListId", ((TaskList) insertTaskListResponse.getMessage().getPayload()).getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			lookupFlowConstruct("delete-task-list").process(getTestEvent(testObjects));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testGetTaskListById() {
		MuleEvent getTaskListByIdResponse = null;
		try {
			getTaskListByIdResponse = lookupFlowConstruct("get-task-list-by-id")
					.process(getTestEvent(testObjects));
			assertEquals("get-task-list-by-id should return the task inserted in the setUp",
					((TaskList) testObjects.get("taskListRef")).getTitle(),
					((TaskList) getTaskListByIdResponse.getMessage()
							.getPayload()).getTitle());
		} catch (Exception e1) {
			e1.printStackTrace();
			fail();
		}
	}
}
