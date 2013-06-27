package org.mule.module.google.task.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.module.google.task.model.TaskList;

public class InsertTaskListTestCases extends GoogleTaskTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		testObjects = (HashMap<String, Object>) context.getBean("insertTaskList");
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
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testInsertTaskList() {
		MuleEvent insertTaskListResponse = null;
		try {
			insertTaskListResponse = lookupFlowConstruct("insert-task-list").process(getTestEvent(testObjects));
		} catch (Exception e1) {
			e1.printStackTrace();
			fail();
		}
		
		assertTrue("The object returned by insert-task-list should be of type TaskList", 
				insertTaskListResponse.getMessage().getPayload() instanceof TaskList);
		assertEquals("The title of the TaskList returned by insert-task-list should be equal to the title of the TaskList inserted",
				((TaskList) testObjects.get("taskListRef")).getTitle(),
				((TaskList) insertTaskListResponse.getMessage().getPayload()).getTitle());
		
		// add taskListId of the inserted list to query for the list (and to delete it in tearDown)
		testObjects.put("taskListId", ((TaskList) insertTaskListResponse.getMessage().getPayload()).getId());

		MuleEvent getTaskListByIdResponse = null;
		try{
			getTaskListByIdResponse = lookupFlowConstruct("get-task-list-by-id").process(getTestEvent(testObjects));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertTrue("The object returned by get-task-list-by-id should be of type TaskList", 
				getTaskListByIdResponse.getMessage().getPayload() instanceof TaskList);
		assertEquals("The title of the TaskList returned by get-task-list-by-id should be equal to the title of the TaskList inserted",
				((TaskList) testObjects.get("taskListRef")).getTitle(),
				((TaskList) getTaskListByIdResponse.getMessage().getPayload()).getTitle());
	}
}
