/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.google.task.automation.testcases;

import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.mule.api.config.MuleProperties;
import org.mule.api.store.ObjectStore;
import org.mule.api.store.ObjectStoreException;
import org.mule.common.security.oauth.OAuthState;
import org.mule.module.google.task.model.Task;
import org.mule.module.google.task.model.TaskList;
import org.mule.modules.tests.ConnectorTestCase;
import org.springframework.context.ApplicationContext;
//import org.mule.module.google.task.oauth.GoogleTasksConnectorOAuthState;

public class GoogleTaskTestParent extends ConnectorTestCase {
	
	// Set global timeout of tests to 10minutes
    @Rule
    public Timeout globalTimeout = new Timeout(600000);
    
	protected static final String[] SPRING_CONFIG_FILES = new String[] { "AutomationSpringBeans.xml" };
	protected static ApplicationContext context;
	protected Map<String, Object> testObjects;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Before
	public void init() throws ObjectStoreException {
		ObjectStore objectStore = muleContext.getRegistry().lookupObject(MuleProperties.DEFAULT_USER_OBJECT_STORE_NAME);
		objectStore.store("accessTokenId", (OAuthState) getBeanFromContext("connectorOAuthState"));
	}
	
	protected void setTestObjects(Map<String, Object> testObjects) {
		this.testObjects = testObjects;
	}
	
	protected Task insertTask(Task task, TaskList taskList) throws Exception {
		return insertTask(task, taskList.getId());
	}

	protected Task insertTask(Task task, String taskListId) throws Exception {
		upsertOnTestRunMessage("taskRef", task);
		upsertOnTestRunMessage("taskListId", taskListId);

		return (Task) runFlowAndGetPayload("insert-task");
	}
	
}
