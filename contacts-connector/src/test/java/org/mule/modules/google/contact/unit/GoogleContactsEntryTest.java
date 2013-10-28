/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.google.contact.unit;

import junit.framework.Assert;

import org.junit.Test;
import org.mule.modules.google.contact.wrappers.GoogleContactEntry;

public class GoogleContactsEntryTest {
	
	@Test
	public void checkNamesPersistence() {
		final String VALUE = "imSomeValue";
		
		GoogleContactEntry gce = new GoogleContactEntry();
		
		gce.setFamilyName(VALUE);
		Assert.assertEquals(VALUE, gce.getFamilyName());
		
		gce.setGivenName(VALUE);
		Assert.assertEquals(VALUE, gce.getGivenName());
		
		gce.setFullName(VALUE);
		Assert.assertEquals(VALUE, gce.getFullName());
	}
}
