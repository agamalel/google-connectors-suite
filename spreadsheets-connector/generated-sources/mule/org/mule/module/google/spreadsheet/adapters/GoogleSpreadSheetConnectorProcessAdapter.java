
package org.mule.module.google.spreadsheet.adapters;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.module.google.spreadsheet.GoogleSpreadSheetConnector;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>GoogleSpreadSheetConnectorProcessAdapter</code> is a wrapper around {@link GoogleSpreadSheetConnector } that enables custom processing strategies.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-20T04:29:29-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class GoogleSpreadSheetConnectorProcessAdapter
    extends GoogleSpreadSheetConnectorLifecycleAdapter
    implements ProcessAdapter<GoogleSpreadSheetConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, GoogleSpreadSheetConnectorCapabilitiesAdapter> getProcessTemplate() {
        final GoogleSpreadSheetConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,GoogleSpreadSheetConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, GoogleSpreadSheetConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, GoogleSpreadSheetConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
