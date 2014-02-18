
package org.mule.module.google.spreadsheet.config;

import javax.annotation.Generated;
import org.mule.api.processor.MessageProcessor;
import org.mule.config.spring.factories.MessageProcessorChainFactoryBean;
import org.mule.security.oauth.callback.DefaultSaveAccessTokenCallback;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-18T03:27:46-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class SaveAccessTokenCallbackFactoryBean
    extends MessageProcessorChainFactoryBean
{


    public Class getObjectType() {
        return DefaultSaveAccessTokenCallback.class;
    }

    public Object getObject()
        throws Exception
    {
        DefaultSaveAccessTokenCallback callback = new DefaultSaveAccessTokenCallback();
        callback.setMessageProcessor(((MessageProcessor) super.getObject()));
        return callback;
    }

}
