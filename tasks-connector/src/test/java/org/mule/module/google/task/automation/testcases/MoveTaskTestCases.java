package org.mule.module.google.task.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.task.model.Task;
import org.mule.module.google.task.model.TaskList;

public class MoveTaskTestCases extends GoogleTaskTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			
			testObjects = (HashMap<String, Object>) context.getBean("moveTask");
		
			MessageProcessor flow = lookupFlowConstruct("insert-task-list");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			TaskList taskList = (TaskList) response.getMessage().getPayload();
			
			testObjects.put("taskList", taskList);
			testObjects.put("taskListId", taskList.getId());
			
			Task firstTask = insertTask((Task) testObjects.get("firstTask"), taskList);
			Task secondTask = insertTask((Task) testObjects.get("secondTask"), taskList);
			
			testObjects.put("firstTask", firstTask);
			testObjects.put("firstTaskId", firstTask.getId());
			testObjects.put("secondTask", secondTask);
			testObjects.put("secondTaskId", secondTask.getId());

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testMoveTask_Parent() {
		try {
			
			Task firstTask = (Task) testObjects.get("firstTask");
			Task secondTask = (Task) testObjects.get("secondTask");
			
			testObjects.put("parentId", firstTask.getId());
			testObjects.put("taskId", secondTask.getId());
			
			MessageProcessor flow = lookupFlowConstruct("move-task-parent");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Task movedTask = (Task) response.getMessage().getPayload();
			assertTrue(movedTask.getParent().equals(firstTask.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testMoveTask_Reorder() {
		try {
			
			Task firstTask = (Task) testObjects.get("firstTask");
			Task secondTask = (Task) testObjects.get("secondTask");
			
			// Check that the first task comes after the second task
			// Tasks which are inserted first have a larger "position" value (lexicographically)
			// than those which are inserted after.
			assertTrue(firstTask.getPosition().compareTo(secondTask.getPosition()) == 1);
			
			testObjects.put("previousId", firstTask.getId());
			testObjects.put("taskId", secondTask.getId());
			
			MessageProcessor flow = lookupFlowConstruct("move-task-reorder");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Task movedTask = (Task) response.getMessage().getPayload();
			assertTrue(movedTask.getPosition().compareTo(secondTask.getPosition()) == 1);
		
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@After
	public void tearDown() {
		try {
			MessageProcessor flow = lookupFlowConstruct("delete-task-list");
			MuleEvent response = flow.process(getTestEvent(testObjects));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
