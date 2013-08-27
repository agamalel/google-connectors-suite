package org.mule.module.google.spreadsheet.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.spreadsheet.model.Worksheet;


public class CreateWorksheetTestCases extends GoogleSpreadsheetsTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("createWorksheet");
			
			String spreadsheetTitle = (String) testObjects.get("spreadsheet");
			createSpreadsheet(spreadsheetTitle);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void createWorksheet() {
		try {
			String title = (String) testObjects.get("title");
			int rowCount = (Integer) testObjects.get("rowCount");
			int colCount = (Integer) testObjects.get("colCount");
			
			MessageProcessor flow = lookupFlowConstruct("create-worksheet");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Worksheet worksheet = (Worksheet) response.getMessage().getPayload();
			assertTrue(worksheet != null);
			assertTrue(worksheet.getName().equals(title));
			assertTrue(worksheet.getRowCount() == rowCount);
			assertTrue(worksheet.getColCount() == colCount);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
