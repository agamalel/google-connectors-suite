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
import org.mule.module.google.spreadsheet.CsvToRowsAdapter;
import org.mule.module.google.spreadsheet.model.Row;
import org.mule.module.google.spreadsheet.model.Worksheet;

public class GetAllCellsAsCsvTestCases extends GoogleSpreadsheetsTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getAllCellsAsCsv");
			
			String spreadsheetTitle = (String) testObjects.get("spreadsheet");
			createSpreadsheet(spreadsheetTitle);

			String worksheetTitle = (String) testObjects.get("worksheet");
			int rowCount = (Integer) testObjects.get("rowCount");
			int colCount = (Integer) testObjects.get("colCount");
			
			Worksheet worksheet = createWorksheet(spreadsheetTitle, worksheetTitle, rowCount, colCount);
			testObjects.put("worksheetObject", worksheet);
			
			List<Row> rows = (List<Row>) testObjects.get("rowsRef");
			setRowValues(spreadsheetTitle, worksheet.getTitle(), rows);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetAllCellsAsCsv() {
		try {
			String columnSeparator = (String) testObjects.get("columnSeparator");
			String lineSeparator = (String) testObjects.get("lineSeparator");
			
			List<Row> inputRows = (List<Row>) testObjects.get("rowsRef");
			
			MessageProcessor flow = lookupFlowConstruct("get-all-cells-as-csv");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			String csvCells = (String) response.getMessage().getPayload();
			
			List<Row> rowsFromCsv = CsvToRowsAdapter.adapt(csvCells, 1, 1, columnSeparator, lineSeparator);

			for (Row row : inputRows) {
				assertTrue(rowsFromCsv.contains(row));
				Row retrievedRow = rowsFromCsv.get(rowsFromCsv.indexOf(row));
				
				boolean equals = isRowEqual(row, retrievedRow);
				assertTrue(equals);
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
			String spreadsheet = (String) testObjects.get("spreadsheet");
			deleteSpreadsheet(spreadsheet);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
