/**
 * Mule Google Contacts Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
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
