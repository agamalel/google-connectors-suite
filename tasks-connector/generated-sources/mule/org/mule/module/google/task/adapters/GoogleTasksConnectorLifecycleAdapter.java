
package org.mule.module.google.task.adapters;

import javax.annotation.Generated;
import org.mule.api.MuleException;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.common.MuleVersion;
import org.mule.config.MuleManifest;
import org.mule.config.i18n.CoreMessages;
import org.mule.module.google.task.GoogleTasksConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A <code>GoogleTasksConnectorLifecycleAdapter</code> is a wrapper around {@link GoogleTasksConnector } that adds lifecycle methods to the pojo.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-18T03:28:27-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class GoogleTasksConnectorLifecycleAdapter
    extends GoogleTasksConnectorMetadataAdapater
    implements Disposable, Initialisable, Startable, Stoppable
{


    @Override
    public void start()
        throws MuleException
    {
    }

    @Override
    public void stop()
        throws MuleException
    {
    }

    @Override
    public void initialise()
        throws InitialisationException
    {
        Logger log = LoggerFactory.getLogger(GoogleTasksConnectorLifecycleAdapter.class);
        MuleVersion connectorVersion = new MuleVersion("3.5");
        MuleVersion muleVersion = new MuleVersion(MuleManifest.getProductVersion());
        if (!muleVersion.atLeastBase(connectorVersion)) {
            throw new InitialisationException(CoreMessages.minMuleVersionNotMet(this.getMinMuleVersion()), this);
        }
    }

    @Override
    public void dispose() {
    }

}
