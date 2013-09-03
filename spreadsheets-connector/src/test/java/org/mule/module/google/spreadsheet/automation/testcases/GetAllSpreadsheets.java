package org.mule.module.google.spreadsheet.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.spreadsheet.model.Spreadsheet;

public class GetAllSpreadsheets extends GoogleSpreadsheetsTestParent {


	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getAllSpreadsheets");
			
			List<String> spreadsheetTitles = (List<String>) testObjects.get("spreadsheets");
			for (String spreadsheetTitle : spreadsheetTitles) {
				createSpreadsheet(spreadsheetTitle);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetAllSpreadsheets() {
		try {
			List<String> createdSpreadsheets = (List<String>) testObjects.get("spreadsheets");

			MessageProcessor flow = lookupFlowConstruct("get-all-spreadsheets");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			List<Spreadsheet> spreadsheets = (List<Spreadsheet>) response.getMessage().getPayload();

			for (String spreadsheetTitle : createdSpreadsheets) {
				boolean found = false;
				for (Spreadsheet spreadsheet : spreadsheets) {
					if (spreadsheet.getTitle().equals(spreadsheetTitle)) {
						found = true;
						break;
					}
				}
				assertTrue(found);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@After
	public void tearDown() {
		try {
			List<String> spreadsheets = (List<String>) testObjects.get("spreadsheets");
			for (String spreadsheet : spreadsheets) {
				deleteSpreadsheet(spreadsheet);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
