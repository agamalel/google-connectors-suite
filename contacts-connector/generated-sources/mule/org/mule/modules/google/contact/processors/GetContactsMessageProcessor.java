
package org.mule.modules.google.contact.processors;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import com.google.gdata.client.contacts.ContactQuery.OrderBy;
import com.google.gdata.client.contacts.ContactQuery.SortOrder;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.config.ConfigurationException;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.registry.RegistrationException;
import org.mule.common.DefaultResult;
import org.mule.common.FailureType;
import org.mule.common.Result;
import org.mule.common.metadata.ConnectorMetaDataEnabled;
import org.mule.common.metadata.DefaultListMetaDataModel;
import org.mule.common.metadata.DefaultMetaData;
import org.mule.common.metadata.DefaultPojoMetaDataModel;
import org.mule.common.metadata.DefaultSimpleMetaDataModel;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.OperationMetaDataEnabled;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.datatype.DataTypeFactory;
import org.mule.modules.google.contact.GoogleContactsConnector;
import org.mule.modules.google.contact.oauth.GoogleContactsConnectorOAuthManager;
import org.mule.modules.google.contact.wrappers.GoogleContactEntry;
import org.mule.modules.google.oauth.invalidation.OAuthTokenExpiredException;
import org.mule.security.oauth.callback.ProcessCallback;
import org.mule.streaming.PagingConfiguration;
import org.mule.streaming.PagingDelegate;


/**
 * GetContactsMessageProcessor invokes the {@link org.mule.modules.google.contact.GoogleContactsConnector#getContacts(java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.google.gdata.client.contacts.ContactQuery.SortOrder, java.lang.Boolean, com.google.gdata.client.contacts.ContactQuery.OrderBy, java.lang.String, org.mule.streaming.PagingConfiguration)} method in {@link GoogleContactsConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-18T03:27:05-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class GetContactsMessageProcessor
    extends AbstractPagedConnectedProcessor
    implements MessageProcessor, OperationMetaDataEnabled
{

    protected Object updatedMin;
    protected String _updatedMinType;
    protected Object updatedMax;
    protected String _updatedMaxType;
    protected Object datetimeFormat;
    protected String _datetimeFormatType;
    protected Object fullTextQuery;
    protected String _fullTextQueryType;
    protected Object sortOrder;
    protected SortOrder _sortOrderType;
    protected Object showDeleted;
    protected Boolean _showDeletedType;
    protected Object orderBy;
    protected OrderBy _orderByType;
    protected Object groupId;
    protected String _groupIdType;
    protected Object pagingConfiguration;
    protected PagingConfiguration _pagingConfigurationType;

    public GetContactsMessageProcessor(String operationName) {
        super(operationName);
    }

    /**
     * Obtains the expression manager from the Mule context and initialises the connector. If a target object  has not been set already it will search the Mule registry for a default one.
     * 
     * @throws InitialisationException
     */
    public void initialise()
        throws InitialisationException
    {
    }

    @Override
    public void start()
        throws MuleException
    {
        super.start();
    }

    @Override
    public void stop()
        throws MuleException
    {
        super.stop();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    /**
     * Sets pagingConfiguration
     * 
     * @param value Value to set
     */
    public void setPagingConfiguration(Object value) {
        this.pagingConfiguration = value;
    }

    /**
     * Sets updatedMax
     * 
     * @param value Value to set
     */
    public void setUpdatedMax(Object value) {
        this.updatedMax = value;
    }

    /**
     * Sets groupId
     * 
     * @param value Value to set
     */
    public void setGroupId(Object value) {
        this.groupId = value;
    }

    /**
     * Sets orderBy
     * 
     * @param value Value to set
     */
    public void setOrderBy(Object value) {
        this.orderBy = value;
    }

    /**
     * Sets sortOrder
     * 
     * @param value Value to set
     */
    public void setSortOrder(Object value) {
        this.sortOrder = value;
    }

    /**
     * Sets datetimeFormat
     * 
     * @param value Value to set
     */
    public void setDatetimeFormat(Object value) {
        this.datetimeFormat = value;
    }

    /**
     * Sets showDeleted
     * 
     * @param value Value to set
     */
    public void setShowDeleted(Object value) {
        this.showDeleted = value;
    }

    /**
     * Sets fullTextQuery
     * 
     * @param value Value to set
     */
    public void setFullTextQuery(Object value) {
        this.fullTextQuery = value;
    }

    /**
     * Sets updatedMin
     * 
     * @param value Value to set
     */
    public void setUpdatedMin(Object value) {
        this.updatedMin = value;
    }

    /**
     * Invokes the MessageProcessor.
     * 
     * @param event MuleEvent to be processed
     * @throws Exception
     */
    public PagingDelegate getPagingDelegate(final MuleEvent event, final PagingConfiguration pagingConfiguration)
        throws Exception
    {
        Object moduleObject = null;
        try {
            moduleObject = findOrCreate(GoogleContactsConnectorOAuthManager.class, false, event);
            final String _transformedUpdatedMin = ((String) evaluateAndTransform(getMuleContext(), event, GetContactsMessageProcessor.class.getDeclaredField("_updatedMinType").getGenericType(), null, updatedMin));
            final String _transformedUpdatedMax = ((String) evaluateAndTransform(getMuleContext(), event, GetContactsMessageProcessor.class.getDeclaredField("_updatedMaxType").getGenericType(), null, updatedMax));
            final String _transformedDatetimeFormat = ((String) evaluateAndTransform(getMuleContext(), event, GetContactsMessageProcessor.class.getDeclaredField("_datetimeFormatType").getGenericType(), null, datetimeFormat));
            final String _transformedFullTextQuery = ((String) evaluateAndTransform(getMuleContext(), event, GetContactsMessageProcessor.class.getDeclaredField("_fullTextQueryType").getGenericType(), null, fullTextQuery));
            final SortOrder _transformedSortOrder = ((SortOrder) evaluateAndTransform(getMuleContext(), event, GetContactsMessageProcessor.class.getDeclaredField("_sortOrderType").getGenericType(), null, sortOrder));
            final Boolean _transformedShowDeleted = ((Boolean) evaluateAndTransform(getMuleContext(), event, GetContactsMessageProcessor.class.getDeclaredField("_showDeletedType").getGenericType(), null, showDeleted));
            final OrderBy _transformedOrderBy = ((OrderBy) evaluateAndTransform(getMuleContext(), event, GetContactsMessageProcessor.class.getDeclaredField("_orderByType").getGenericType(), null, orderBy));
            final String _transformedGroupId = ((String) evaluateAndTransform(getMuleContext(), event, GetContactsMessageProcessor.class.getDeclaredField("_groupIdType").getGenericType(), null, groupId));
            final PagingConfiguration _transformedPagingConfiguration = ((PagingConfiguration) evaluateAndTransform(getMuleContext(), event, GetContactsMessageProcessor.class.getDeclaredField("_pagingConfigurationType").getGenericType(), null, pagingConfiguration));
            Object resultPayload;
            ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
            resultPayload = processTemplate.execute(new ProcessCallback<Object,Object>() {


                public List<Class<? extends Exception>> getManagedExceptions() {
                    return Arrays.asList(((Class<? extends Exception> []) new Class[] {OAuthTokenExpiredException.class }));
                }

                public boolean isProtected() {
                    return true;
                }

                public Object process(Object object)
                    throws Exception
                {
                    return ((GoogleContactsConnector) object).getContacts(_transformedUpdatedMin, _transformedUpdatedMax, _transformedDatetimeFormat, _transformedFullTextQuery, _transformedSortOrder, _transformedShowDeleted, _transformedOrderBy, _transformedGroupId, _transformedPagingConfiguration);
                }

            }
            , this, event);
            return ((PagingDelegate) resultPayload);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Result<MetaData> getInputMetaData() {
        return new DefaultResult<MetaData>(null, (Result.Status.SUCCESS));
    }

    @Override
    public Result<MetaData> getOutputMetaData(MetaData inputMetadata) {
        return new DefaultResult<MetaData>(new DefaultMetaData(new DefaultListMetaDataModel(getPojoOrSimpleModel(GoogleContactEntry.class))));
    }

    private MetaDataModel getPojoOrSimpleModel(Class clazz) {
        DataType dataType = DataTypeFactory.getInstance().getDataType(clazz);
        if (DataType.POJO.equals(dataType)) {
            return new DefaultPojoMetaDataModel(clazz);
        } else {
            return new DefaultSimpleMetaDataModel(dataType);
        }
    }

    public Result<MetaData> getGenericMetaData(MetaDataKey metaDataKey) {
        ConnectorMetaDataEnabled connector;
        try {
            connector = ((ConnectorMetaDataEnabled) findOrCreate(GoogleContactsConnector.class, true, null));
            try {
                Result<MetaData> metadata = connector.getMetaData(metaDataKey);
                if ((Result.Status.FAILURE).equals(metadata.getStatus())) {
                    return metadata;
                }
                if (metadata.get() == null) {
                    return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error processing metadata at GoogleContactsConnector at getContacts retrieving was successful but result is null");
                }
                return metadata;
            } catch (Exception e) {
                return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
            }
        } catch (ClassCastException cast) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error getting metadata, there was no connection manager available. Maybe you're trying to use metadata from an Oauth connector");
        } catch (ConfigurationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (RegistrationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (IllegalAccessException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (InstantiationException e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        } catch (Exception e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), e.getMessage(), FailureType.UNSPECIFIED, e);
        }
    }

}
