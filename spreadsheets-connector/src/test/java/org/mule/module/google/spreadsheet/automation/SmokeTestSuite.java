package org.mule.module.google.spreadsheet.automation;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.module.google.spreadsheet.automation.testcases.CreateSpreadsheetTestCases;
import org.mule.module.google.spreadsheet.automation.testcases.CreateWorksheetTestCases;
import org.mule.module.google.spreadsheet.automation.testcases.GetAllCellsTestCases;
import org.mule.module.google.spreadsheet.automation.testcases.GetAllSpreadsheetsTestCases;
import org.mule.module.google.spreadsheet.automation.testcases.GetWorksheetByTitleTestCases;
import org.mule.module.google.spreadsheet.automation.testcases.SetRowValuesTestCases;
import org.mule.module.google.spreadsheet.automation.testcases.SmokeTests;

@RunWith(Categories.class)
@IncludeCategory(SmokeTests.class)
@SuiteClasses({
	CreateSpreadsheetTestCases.class,
	CreateWorksheetTestCases.class,
	GetAllSpreadsheetsTestCases.class,
	GetWorksheetByTitleTestCases.class,
	SetRowValuesTestCases.class,
	GetAllCellsTestCases.class
})
public class SmokeTestSuite {

}
