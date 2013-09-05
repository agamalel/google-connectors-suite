package org.mule.modules.google.contact.automation.testcases;

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
		return deleteContacts(batchId, operationId, Arrays.asList(entries));
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
	
}
