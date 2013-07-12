/**
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 **/

package org.mule.module.gcm;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

/**
 * A {@link MessageListener} that receives CCS messages.
 * 
 * @author MuleSoft, Inc.
 */
public class CcsMessageListener implements MessageListener
{
    @Override
    public void processMessage(final Chat chat, final Message message)
    {
        // TODO Auto-generated method stub

    }
}
