
package org.mule.module.google.task.adapters;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.module.google.task.GoogleTasksConnector;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>GoogleTasksConnectorProcessAdapter</code> is a wrapper around {@link GoogleTasksConnector } that enables custom processing strategies.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T11:47:49-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class GoogleTasksConnectorProcessAdapter
    extends GoogleTasksConnectorLifecycleAdapter
    implements ProcessAdapter<GoogleTasksConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, GoogleTasksConnectorCapabilitiesAdapter> getProcessTemplate() {
        final GoogleTasksConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,GoogleTasksConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, GoogleTasksConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, GoogleTasksConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
