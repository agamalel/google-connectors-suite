/**
 * Mule Google Calendars Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.google.calendar;

import org.mockito.Mockito;

import com.google.api.services.calendar.Calendar.CalendarList;
import com.google.api.services.calendar.Calendar.Calendars;
import com.google.api.services.calendar.Calendar.Calendars.Insert;
import com.google.api.services.calendar.model.Calendar;


/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class GoogleCalendarConnectorTestCase extends BaseGoogleConnectorTest<GoogleCalendarConnector> {

	private com.google.api.services.calendar.Calendar client;
	private Calendars calendars;
	private CalendarList calendarList;
	private Calendar testCalendar;
	
	@Override
	protected void doSetUp() {
		
		client = Mockito.mock(com.google.api.services.calendar.Calendar.class);
		calendars = Mockito.mock(Calendars.class);
		calendarList = Mockito.mock(CalendarList.class);
		
		Mockito.when(client.calendars()).thenReturn(calendars);
		Mockito.when(client.calendarList()).thenReturn(calendarList);
		
		this.connector = new GoogleCalendarConnector();
		this.connector.setClientFactory(new GoogleCalendarClientFactory() {
			
			@Override
			public com.google.api.services.calendar.Calendar newClient(String accessToken, String applicationName) {
				return client;
			}
		});
		
		this.connector.init();
		this.connector.postAuth();
		
		this.testCalendar = new Calendar();
		
	}
	
	public void testCreateCalendar() throws Exception {
		Insert insert = Mockito.mock(Insert.class);
		Mockito.when(insert.execute()).thenReturn(this.testCalendar);
		Mockito.when(calendars.insert(Mockito.any(Calendar.class))).thenReturn(insert);
		
		org.mule.module.google.calendar.model.Calendar cal = this.connector.createCalendar(new org.mule.module.google.calendar.model.Calendar());
		assertSame(cal.wrapped(), this.testCalendar);
	}
	
}
