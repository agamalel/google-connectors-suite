package org.mule.modules.google.contact.automation;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.modules.google.contact.automation.testcases.BatchDeleteContactsTestCases;
import org.mule.modules.google.contact.automation.testcases.BatchInsertContactsTestCases;
import org.mule.modules.google.contact.automation.testcases.BatchUpdateContactsTestCases;
import org.mule.modules.google.contact.automation.testcases.CreateGroupTestCases;
import org.mule.modules.google.contact.automation.testcases.DeleteContactByIdTestCases;
import org.mule.modules.google.contact.automation.testcases.DeleteContactTestCases;
import org.mule.modules.google.contact.automation.testcases.GetContactByIdTestCases;
import org.mule.modules.google.contact.automation.testcases.InsertContactTestCases;

@RunWith(Categories.class)
@IncludeCategory(SmokeTests.class)
@SuiteClasses({
	BatchDeleteContactsTestCases.class,
	BatchInsertContactsTestCases.class,
	BatchUpdateContactsTestCases.class,
	CreateGroupTestCases.class,
	DeleteContactByIdTestCases.class,
	DeleteContactTestCases.class,
	GetContactByIdTestCases.class,
	InsertContactTestCases.class
})
public class SmokeTests {

}
