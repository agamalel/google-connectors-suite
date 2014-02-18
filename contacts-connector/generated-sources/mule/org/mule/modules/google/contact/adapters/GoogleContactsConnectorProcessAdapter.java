
package org.mule.modules.google.contact.adapters;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.modules.google.contact.GoogleContactsConnector;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>GoogleContactsConnectorProcessAdapter</code> is a wrapper around {@link GoogleContactsConnector } that enables custom processing strategies.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-18T03:27:05-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class GoogleContactsConnectorProcessAdapter
    extends GoogleContactsConnectorLifecycleAdapter
    implements ProcessAdapter<GoogleContactsConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, GoogleContactsConnectorCapabilitiesAdapter> getProcessTemplate() {
        final GoogleContactsConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,GoogleContactsConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, GoogleContactsConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, GoogleContactsConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
