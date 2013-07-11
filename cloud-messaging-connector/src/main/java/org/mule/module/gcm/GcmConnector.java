/**
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 **/

package org.mule.module.gcm;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.codehaus.jackson.map.ObjectMapper;
import org.mule.api.MessagingException;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Module;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.api.context.MuleContextAware;
import org.mule.config.i18n.MessageFactory;
import org.mule.module.gcm.model.Data;
import org.mule.module.gcm.model.GcmRequest;
import org.mule.module.gcm.model.GcmResponse;
import org.mule.transformer.types.MimeTypes;
import org.mule.transport.http.HttpConnector;
import org.mule.transport.http.HttpConstants;
import org.mule.util.MapUtils;
import org.mule.util.NumberUtils;

/**
 * Mule connector for Google Cloud Messaging (GCM / CCS).
 * <p/>
 * {@sample.xml ../../../doc/gcm-connector.xml.sample gcm:config}
 * 
 * @author MuleSoft, Inc.
 */
@Module(name = "gcm", schemaVersion = "1.0", friendlyName = "Google Cloud Messaging", minMuleVersion = "3.4", description = "Google Cloud Messaging Connector")
public class GcmConnector implements MuleContextAware
{
    // TODO support CCS (XMPP)

    public static final String GCM_SEND_URI = "https://android.googleapis.com/gcm/send";
    public static final String GCM_NOTIFICATION_URI = "https://android.googleapis.com/gcm/notification";

    /**
     * The Google APIs key.
     */
    @Configurable
    private String apiKey;

    /**
     * The connector to use to reach Neo4j: configure only if there is more than one HTTP/HTTPS
     * connector active in your Mule application.
     */
    @Configurable
    @Optional
    private org.mule.api.transport.Connector connector;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private MuleContext muleContext;

    /**
     * Send a message using the HTTP API.
     * <p/>
     * {@sample.xml ../../../doc/gcm-connector.xml.sample gcm:send-message-no-data}
     * <p/>
     * {@sample.xml ../../../doc/gcm-connector.xml.sample gcm:send-message-with-data}
     * 
     * @param registrationIds the list of devices (registration IDs) receiving the message.
     * @param notificationKey a string that maps a single user to multiple registration IDs
     *            associated with that user.
     * @param notificationKeyName a name or identifier that is unique to a given user.
     * @param collapseKey an arbitrary string that is used to collapse a group of like messages when
     *            the device is offline, so that only the last message gets sent to the client.
     * @param data the key-value pairs of the message's payload data.
     * @param delayWhileIdle indicates that the message should not be sent immediately if the device
     *            is idle.
     * @param timeToLiveSeconds how long (in seconds) the message should be kept on GCM storage if
     *            the device is offline.
     * @param restrictedPackageName a string containing the package name of your application.
     * @param dryRun allows developers to test their request without actually sending a message.
     * @param muleEvent the {@link MuleEvent} being processed.
     * @return a {@link GcmResponse} instance.
     * @throws Exception thrown in case anything goes wrong when sending the message.
     */
    @Processor
    @Inject
    public GcmResponse sendMessage(final List<String> registrationIds,
                                   @Optional final String notificationKey,
                                   @Optional final String notificationKeyName,
                                   @Optional final String collapseKey,
                                   @Optional final Map<String, Object> data,
                                   @Optional @Default("false") final boolean delayWhileIdle,
                                   @Optional @Default("2419200") final int timeToLiveSeconds,
                                   @Optional final String restrictedPackageName,
                                   @Optional @Default("false") final boolean dryRun,
                                   final MuleEvent muleEvent) throws Exception
    {
        final GcmRequest gcmRequest = new GcmRequest();
        gcmRequest.getRegistrationIds().addAll(registrationIds);
        gcmRequest.setNotificationKey(notificationKeyName);
        gcmRequest.setNotificationKeyName(notificationKeyName);
        gcmRequest.setCollapseKey(collapseKey);
        gcmRequest.setDelayWhileIdle(delayWhileIdle);
        gcmRequest.setTimeToLive(timeToLiveSeconds);
        gcmRequest.setRestrictedPackageName(restrictedPackageName);
        gcmRequest.setDryRun(dryRun);

        if (MapUtils.isNotEmpty(data))
        {
            final Data gcmData = new Data();
            gcmData.getAdditionalProperties().putAll(data);
            gcmRequest.setData(gcmData);
        }

        final String gcmRequestJson = OBJECT_MAPPER.writeValueAsString(gcmRequest);

        final String uri = GCM_SEND_URI + (connector != null ? "?connector=" + connector.getName() : "");

        final Map<String, Object> requestProperties = new HashMap<String, Object>();
        requestProperties.put(HttpConstants.HEADER_CONTENT_TYPE, MimeTypes.JSON);
        requestProperties.put(HttpConstants.HEADER_AUTHORIZATION, "key=" + apiKey);

        final MuleMessage response = muleContext.getClient().send(uri, gcmRequestJson, requestProperties,
            muleEvent.getTimeout());

        final Object responseStatusCode = response.getInboundProperty(HttpConnector.HTTP_STATUS_PROPERTY);
        if (NumberUtils.toInt(responseStatusCode) != HttpConstants.SC_OK)
        {
            throw new MessagingException(MessageFactory.createStaticMessage("Received status code: "
                                                                            + responseStatusCode
                                                                            + ", entity:"
                                                                            + response.getPayloadAsString()),
                muleEvent);
        }

        return OBJECT_MAPPER.readValue((InputStream) response.getPayload(), GcmResponse.class);
    }

    @Override
    public void setMuleContext(final MuleContext muleContext)
    {
        this.muleContext = muleContext;
    }

    public String getApiKey()
    {
        return apiKey;
    }

    public void setApiKey(final String apiKey)
    {
        this.apiKey = apiKey;
    }

    public org.mule.api.transport.Connector getConnector()
    {
        return connector;
    }

    public void setConnector(final org.mule.api.transport.Connector connector)
    {
        this.connector = connector;
    }
}
