package org.mule.module.google.spreadsheet.automation.testcases;

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
import org.mule.module.google.spreadsheet.model.Spreadsheet;
import org.mule.module.google.spreadsheet.model.Worksheet;
import org.mule.module.google.spreadsheet.oauth.GoogleSpreadSheetConnectorOAuthState;
import org.mule.tck.junit4.FunctionalTestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GoogleSpreadsheetsTestParent extends FunctionalTestCase {
	
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
		objectStore.store("accessTokenId", (GoogleSpreadSheetConnectorOAuthState)context.getBean("connectorOAuthState"));
	}
	
	@Override
	protected String getConfigResources() {
		return "automation-test-flows.xml";
	}

	protected MessageProcessor lookupFlowConstruct(String name) {
		return (MessageProcessor) muleContext.getRegistry()
				.lookupFlowConstruct(name);
	}

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILES);
	}
	
	public void createSpreadsheet(String title) throws Exception {
		testObjects.put("title", title);
		MessageProcessor flow = lookupFlowConstruct("create-spreadsheet");
		MuleEvent response = flow.process(getTestEvent(testObjects));
	}

	public List<Spreadsheet> getAllSpreadsheets() throws Exception {
		MessageProcessor flow = lookupFlowConstruct("get-all-spreadsheets");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (List<Spreadsheet>) response.getMessage().getPayload();
	}
	
	public Worksheet createWorksheet(String spreadsheet, String title, int rowCount, int colCount) throws Exception {
		testObjects.put("spreadsheet", spreadsheet);
		testObjects.put("title", title);
		testObjects.put("rowCount", rowCount);
		testObjects.put("colCount", colCount);
		
		MessageProcessor flow = lookupFlowConstruct("create-worksheet");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (Worksheet) response.getMessage().getPayload();
	}
	
	public void deleteWorksheet(String spreadsheet, String worksheet) throws Exception {
		testObjects.put("spreadsheet", spreadsheet);
		testObjects.put("worksheet", worksheet);
		
		MessageProcessor flow = lookupFlowConstruct("delete-worksheet");
		MuleEvent response = flow.process(getTestEvent(testObjects));
	}
	
	public List<Worksheet> getWorksheetByTitle(String spreadsheet, String title) throws Exception {
		testObjects.put("spreadsheet", spreadsheet);
		testObjects.put("title", title);
		
		MessageProcessor flow = lookupFlowConstruct("get-worksheet-by-title");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (List<Worksheet>) response.getMessage().getPayload();
	}
	
	public void deleteSpreadsheet(String spreadsheet) throws Exception {
		// Dummy method. Will be implemented when functionality is provided
	}
	
}
