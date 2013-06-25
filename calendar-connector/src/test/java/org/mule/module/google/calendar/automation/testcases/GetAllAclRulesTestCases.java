package org.mule.module.google.calendar.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.calendar.model.AclRule;
import org.mule.module.google.calendar.model.Calendar;

public class GetAllAclRulesTestCases extends GoogleCalendarTestParent {

	protected List<AclRule> insertedAclRules = new ArrayList<AclRule>();
		
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getAllAclRules");
			
			// Insert calendar and get reference to retrieved calendar
			Calendar calendar = insertCalendar((Calendar) testObjects.get("calendarRef"));
			
			// Replace old calendar instance with new instance
			testObjects.put("calendarRef", calendar);
			testObjects.put("calendarId", calendar.getId());
			
			//Creating the first Acl rule 
			MessageProcessor flow = lookupFlowConstruct("insert-acl-rule");
			MuleEvent event = flow.process(getTestEvent(testObjects));
			
			AclRule aclRule = (AclRule) event.getMessage().getPayload();
			insertedAclRules.add(aclRule);
			
			//Changing the scope
			testObjects.put("scope", "andre.schembris@ricston.com");
			
			//Creating the second Acl rule
			flow = lookupFlowConstruct("insert-acl-rule");
			event = flow.process(getTestEvent(testObjects));
			
			aclRule = (AclRule) event.getMessage().getPayload();
			insertedAclRules.add(aclRule);
				
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, SanityTests.class})
	@Test
	public void testGetAllAclRules() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-all-acl-rules");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<AclRule> aclRuleList = (List<AclRule>) response.getMessage().getPayload();
			
			for (AclRule insertedAclRule : insertedAclRules) {
				assertTrue(isAclRuleInList(aclRuleList, insertedAclRule));
			}
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}	
	
	@After
	public void tearDown() {
		try {
			String calendarId = testObjects.get("calendarId").toString();
			deleteCalendar(calendarId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	
}
