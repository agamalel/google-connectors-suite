
package org.mule.module.google.task.processors;

import javax.annotation.Generated;
import org.mule.api.construct.FlowConstructAware;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.task.oauth.GoogleTasksConnectorOAuthManager;
import org.mule.security.oauth.processor.BaseOAuth2UnauthorizeMessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-18T03:28:27-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class UnauthorizeMessageProcessor
    extends BaseOAuth2UnauthorizeMessageProcessor
    implements FlowConstructAware, MessageProcessor
{

    private static Logger logger = LoggerFactory.getLogger(UnauthorizeMessageProcessor.class);

    @Override
    protected Class<GoogleTasksConnectorOAuthManager> getOAuthManagerClass() {
        return GoogleTasksConnectorOAuthManager.class;
    }

}
