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
