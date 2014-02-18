/**
 * Mule Google Contacts Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.google.contact.automation.testcases;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.mule.api.MuleEvent;
import org.mule.api.config.MuleProperties;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.store.ObjectStore;
import org.mule.api.store.ObjectStoreException;
import org.mule.modules.google.api.domain.BatchResult;
import org.mule.modules.google.contact.oauth.GoogleContactsConnectorOAuthState;
import org.mule.modules.google.contact.wrappers.GoogleContactEntry;
import org.mule.modules.google.contact.wrappers.GoogleContactGroupEntry;
import org.mule.tck.junit4.FunctionalTestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GoogleContactsTestParent extends FunctionalTestCase {
	
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
		objectStore.store("accessTokenId", (GoogleContactsConnectorOAuthState)context.getBean("connectorOAuthState"));
	}
	
	@Override
	protected String getConfigResources() {
		return "automation-test-flows.xml";
	}

	protected MessageProcessor lookupFlowConstruct(String name) {
		return (MessageProcessor) muleContext.getRegistry().lookupFlowConstruct(name);
	}

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILES);
	}
	
	public GoogleContactEntry insertContact(GoogleContactEntry contact) throws Exception {
		testObjects.put("contactRef", contact);
		
		MessageProcessor flow = lookupFlowConstruct("insert-contact");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (GoogleContactEntry) response.getMessage().getPayload();
	}
	
	public void deleteContact(GoogleContactEntry entry) throws Exception {
		testObjects.put("entryRef", entry);

		MessageProcessor flow = lookupFlowConstruct("delete-contact");
		MuleEvent response = flow.process(getTestEvent(testObjects));
	}
	
	public void deleteContact(String contactId) throws Exception {
		testObjects.put("contactId", contactId);
		MessageProcessor flow = lookupFlowConstruct("delete-contact-by-id");
		MuleEvent response = flow.process(getTestEvent(testObjects));
	}
	
	public GoogleContactEntry getContact(String id) throws Exception {
		testObjects.put("id", id);
		
		MessageProcessor flow = lookupFlowConstruct("get-contact-by-id");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (GoogleContactEntry) response.getMessage().getPayload();
	}
	
	public GoogleContactGroupEntry createGroup(GoogleContactGroupEntry group) throws Exception {
		testObjects.put("groupRef", group);
		
		MessageProcessor flow = lookupFlowConstruct("create-group");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (GoogleContactGroupEntry) response.getMessage().getPayload();
	}
	
	public void deleteGroup(GoogleContactGroupEntry group) throws Exception {
		testObjects.put("groupRef", group);
		
		MessageProcessor flow = lookupFlowConstruct("delete-group");
		MuleEvent response = flow.process(getTestEvent(testObjects));
	}
	
	public void deleteGroupById(String groupId) throws Exception {
		testObjects.put("groupId", groupId);

		MessageProcessor flow = lookupFlowConstruct("delete-group-by-id");
		MuleEvent response = flow.process(getTestEvent(testObjects));
	}
	
	public GoogleContactGroupEntry getGroupById(String id) throws Exception {
		testObjects.put("id", id);
		
		MessageProcessor flow = lookupFlowConstruct("get-group-by-id");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (GoogleContactGroupEntry) response.getMessage().getPayload();
	}
	
	public GoogleContactGroupEntry getGroupByName(String groupName) throws Exception {
		testObjects.put("groupName", groupName);
		
		MessageProcessor flow = lookupFlowConstruct("get-group-by-name");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (GoogleContactGroupEntry) response.getMessage().getPayload();
	}

	public List<BatchResult> insertContacts(String batchId, String operationId, List<GoogleContactEntry> entries) throws Exception {
		testObjects.put("batchId", batchId);
		testObjects.put("entriesRef", entries);
		testObjects.put("operationId", operationId);
		
		MessageProcessor flow = lookupFlowConstruct("batch-insert-contacts");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (List<BatchResult>) response.getMessage().getPayload();
	}

	public List<BatchResult> insertContacts(String batchId, String operationId, GoogleContactEntry... entries) throws Exception {
		return insertContacts(batchId, operationId, Arrays.asList(entries));
	}

	public List<BatchResult> deleteContacts(String batchId, String operationId, List<GoogleContactEntry> entries) throws Exception {
		testObjects.put("batchId", batchId);
		testObjects.put("entriesRef", entries);
		testObjects.put("operationId", operationId);
		
		MessageProcessor flow = lookupFlowConstruct("batch-delete-contacts");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (List<BatchResult>) response.getMessage().getPayload();
	}

	public List<BatchResult> deleteContacts(String batchId, String operationId, GoogleContactEntry... entries) throws Exception {
		return deleteContacts(batchId, operationId, Arrays.asList(entries));
	}
	
	/**
	 * Updates the contact's photo
	 * @param contactId
	 * The REAL id of the contact. You need to use getRealId(...) before passing it to this method
	 * @param photoPath
	 * The path of the photo in the classpath
	 * @throws Exception
	 * Any exception which occurs is thrown and handled within the test
	 */
	public void updateContactPhoto(String contactId, String photoPath) throws Exception {
		InputStream stream = getClass().getClassLoader().getResourceAsStream(photoPath);
		updateContactPhoto(contactId, stream);
	}
	
	/**
	 * Updates the contact's photo
	 * @param contactId
	 * The REAL id of the contact. You need to use getRealId(...) before passing it to this method
	 * @param inRef
	 * The photo's input stream
	 * @throws Exception
	 * Any exception which occurs is thrown and handled within the test
	 */
	public void updateContactPhoto(String contactId, InputStream inRef) throws Exception {
		testObjects.put("contactId", contactId);
		testObjects.put("inRef", inRef);
		
		MessageProcessor flow = lookupFlowConstruct("update-contact-photo");
		MuleEvent response = flow.process(getTestEvent(testObjects));
	}
	
	/*
	 * This method is used to extract the real ID from the returned ID.
	 * An example of an ID returned by Google is the following:
	 * 
	 * http://www.google.com/m8/feeds/contacts/worstsignupever%40gmail.com/base/6c3356340bebcf96
	 * 
	 * However, this results in the following URL request, resulting in an error returned by Google
	 * 
	 * https://www.google.com/m8/feeds/contacts/default/full/http://www.google.com/m8/feeds/contacts/worstsignupever%40gmail.com/base/6c3356340bebcf96
	 * 
	 * Therefore, we need a method to extract the last value of the URI, so that we can pass it to the connector.
	 * Doing so will result in the following URL, which is of the correct format:
	 * 
	 * https://www.google.com/m8/feeds/contacts/default/full/6c3356340bebcf96
	 */
	public static String getRealId(String id) {
		int lastSlash = id.lastIndexOf("/");
		return id.substring(lastSlash + 1);
	}
	
}
