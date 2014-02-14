
package org.mule.module.google.calendar.adapters;

import javax.annotation.Generated;
import org.mule.api.MetadataAware;
import org.mule.module.google.calendar.GoogleCalendarConnector;


/**
 * A <code>GoogleCalendarConnectorMetadataAdapater</code> is a wrapper around {@link GoogleCalendarConnector } that adds support for querying metadata about the extension.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T11:45:42-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class GoogleCalendarConnectorMetadataAdapater
    extends GoogleCalendarConnectorCapabilitiesAdapter
    implements MetadataAware
{

    private final static String MODULE_NAME = "Google Calendars";
    private final static String MODULE_VERSION = "1.2.4-SNAPSHOT";
    private final static String DEVKIT_VERSION = "3.5.0-SNAPSHOT";
    private final static String DEVKIT_BUILD = "UNKNOWN_BUILDNUMBER";
    private final static String MIN_MULE_VERSION = "3.5";

    public String getModuleName() {
        return MODULE_NAME;
    }

    public String getModuleVersion() {
        return MODULE_VERSION;
    }

    public String getDevkitVersion() {
        return DEVKIT_VERSION;
    }

    public String getDevkitBuild() {
        return DEVKIT_BUILD;
    }

    public String getMinMuleVersion() {
        return MIN_MULE_VERSION;
    }

}
