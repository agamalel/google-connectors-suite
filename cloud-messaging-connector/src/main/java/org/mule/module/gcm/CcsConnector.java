/**
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 **/

package org.mule.module.gcm;

import java.util.Map;
import java.util.UUID;

import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.lang.StringEscapeUtils;
import org.codehaus.jackson.type.TypeReference;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.PacketExtension;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.MuleException;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.module.gcm.model.CcsRequest;
import org.mule.module.gcm.model.CcsResponse;
import org.mule.module.gcm.model.Data;
import org.mule.util.MapUtils;
import org.mule.util.StringUtils;

/**
 * Mule connector for Google Cloud Messaging (HTTP API).
 * <p/>
 * {@sample.xml ../../../doc/ccs-connector.xml.sample ccs:config}
 * 
 * @author MuleSoft, Inc.
 */
@Connector(name = "ccs", schemaVersion = "1.0", friendlyName = "CCS (XMPP)", minMuleVersion = "3.4", description = "Cloud Connection Server Connector")
public class CcsConnector extends AbstractGcmConnector
{
    private static class GcmPacketExtension implements PacketExtension
    {
        private final String xml;

        GcmPacketExtension(final String data)
        {
            final StringBuilder xmlBuilder = new StringBuilder("<").append(getElementName())
                .append("xmlns='")
                .append(getNamespace())
                .append("'>")
                .append(StringEscapeUtils.escapeXml(data))
                .append("</")
                .append(getElementName())
                .append(">");

            xml = xmlBuilder.toString();
        }

        @Override
        public String getElementName()
        {
            return "gcm";
        }

        @Override
        public String getNamespace()
        {
            return "google:mobile:data";
        }

        @Override
        public String toXML()
        {
            return xml;
        }
    }

    private static final TypeReference<CcsResponse> CCS_RESPONSE_TYPE_REFERENCE = new TypeReference<CcsResponse>()
    {
        // NOOP
    };

    /**
     * If set to true, ACK and NACK messages are delivered over the message source, otherwise they
     * are silently dropped.
     */
    @Configurable
    @Optional
    @Default("false")
    private boolean deliverAckNackMessages;

    private String projectId;
    private XMPPConnection xmppConnection;
    private Chat chat;

    @Connect
    public void connect(@ConnectionKey final String projectId) throws ConnectionException
    {
        this.projectId = projectId;

        try
        {
            final ConnectionConfiguration config = new ConnectionConfiguration("gcm.googleapis.com", 5235);
            config.setCompressionEnabled(true);
            config.setSASLAuthenticationEnabled(true);
            config.setSocketFactory(SSLSocketFactory.getDefault());
            xmppConnection = new XMPPConnection(config);
            xmppConnection.connect();
            xmppConnection.login(getLoginUser(), getApiKey());
            xmppConnection.getChatManager().addChatListener(new ChatManagerListener()
            {
                @Override
                public void chatCreated(final Chat chat, final boolean createdLocally)
                {
                    if (!createdLocally)
                    {
                        CcsConnector.this.chat = chat;
                        chat.addMessageListener(new CcsMessageListener());
                    }
                }
            });

        }
        catch (final XMPPException xmppe)
        {
            throw new ConnectionException(ConnectionExceptionCode.INCORRECT_CREDENTIALS, null,
                xmppe.getMessage(), xmppe);
        }
    }

    @Disconnect
    public void disconnect()
    {
        if (xmppConnection != null)
        {
            xmppConnection.disconnect();
            xmppConnection = null;
            chat = null;
        }
    }

    @ConnectionIdentifier
    public String getLoginUser()
    {
        return projectId + "@gcm.googleapis.com";
    }

    @ValidateConnection
    public boolean isConnected()
    {
        return xmppConnection != null && xmppConnection.isConnected() && chat != null;
    }

    @Processor
    public void dispatchMessage(final String registrationId,
                                @Optional final String messageId,
                                @Optional final Map<String, Object> data,
                                @Optional @Default("false") final boolean delayWhileIdle,
                                @Optional @Default("2419200") final int timeToLiveSeconds) throws Exception
    {
        final CcsRequest ccsRequest = new CcsRequest();
        ccsRequest.setTo(registrationId);
        ccsRequest.setDelayWhileIdle(delayWhileIdle);
        ccsRequest.setTimeToLive(timeToLiveSeconds);

        if (StringUtils.isBlank(messageId))
        {
            ccsRequest.setMessageId(UUID.randomUUID().toString());
        }
        else
        {
            ccsRequest.setMessageId(messageId);
        }

        if (MapUtils.isEmpty(data))
        {
            final Data ccsData = new Data();
            ccsData.getAdditionalProperties().putAll(data);
            ccsRequest.setData(ccsData);
        }

        xmppSend(ccsRequest);
    }

    @Processor
    public void ackMessage(final String registrationId, final String messageId) throws Exception
    {
        final CcsRequest ccsRequest = new CcsRequest();
        ccsRequest.setTo(registrationId);
        ccsRequest.setMessageId(messageId);

        xmppSend(ccsRequest);
    }

    private void xmppSend(final CcsRequest ccsRequest) throws MuleException, XMPPException
    {
        final String jsonRequest = serializeEntityToJson(ccsRequest);
        final Message xmppMessage = new Message();
        xmppMessage.addExtension(new GcmPacketExtension(jsonRequest));
        chat.sendMessage(xmppMessage);
    }

    // TODO add @Source

    public boolean isDeliverAckNackMessages()
    {
        return deliverAckNackMessages;
    }

    public void setDeliverAckNackMessages(final boolean deliverAckNackMessages)
    {
        this.deliverAckNackMessages = deliverAckNackMessages;
    }
}
