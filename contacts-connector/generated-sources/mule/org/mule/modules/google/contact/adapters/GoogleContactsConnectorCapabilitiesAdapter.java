
package org.mule.modules.google.contact.adapters;

import javax.annotation.Generated;
import org.mule.api.devkit.capability.Capabilities;
import org.mule.api.devkit.capability.ModuleCapability;
import org.mule.modules.google.contact.GoogleContactsConnector;


/**
 * A <code>GoogleContactsConnectorCapabilitiesAdapter</code> is a wrapper around {@link GoogleContactsConnector } that implements {@link org.mule.api.Capabilities} interface.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T11:46:32-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class GoogleContactsConnectorCapabilitiesAdapter
    extends GoogleContactsConnector
    implements Capabilities
{


    /**
     * Returns true if this module implements such capability
     * 
     */
    public boolean isCapableOf(ModuleCapability capability) {
        if (capability == ModuleCapability.LIFECYCLE_CAPABLE) {
            return true;
        }
        if (capability == ModuleCapability.OAUTH2_CAPABLE) {
            return true;
        }
        return false;
    }

}
