
package org.mule.module.google.task.processors;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
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
import org.mule.module.google.task.GoogleTasksConnector;
import org.mule.module.google.task.model.Task;
import org.mule.module.google.task.oauth.GoogleTasksConnectorOAuthManager;
import org.mule.modules.google.oauth.invalidation.OAuthTokenExpiredException;
import org.mule.security.oauth.callback.ProcessCallback;
import org.mule.streaming.PagingConfiguration;
import org.mule.streaming.PagingDelegate;


/**
 * GetTasksMessageProcessor invokes the {@link org.mule.module.google.task.GoogleTasksConnector#getTasks(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, boolean, boolean, org.mule.streaming.PagingConfiguration)} method in {@link GoogleTasksConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-20T04:30:03-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class GetTasksMessageProcessor
    extends AbstractPagedConnectedProcessor
    implements MessageProcessor, OperationMetaDataEnabled
{

    protected Object taskListId;
    protected String _taskListIdType;
    protected Object completedMin;
    protected String _completedMinType;
    protected Object completedMax;
    protected String _completedMaxType;
    protected Object dueMin;
    protected String _dueMinType;
    protected Object dueMax;
    protected String _dueMaxType;
    protected Object updatedMin;
    protected String _updatedMinType;
    protected Object showDeleted;
    protected boolean _showDeletedType;
    protected Object showHidden;
    protected boolean _showHiddenType;
    protected Object showcompleted;
    protected boolean _showcompletedType;
    protected Object pagingConfiguration;
    protected PagingConfiguration _pagingConfigurationType;

    public GetTasksMessageProcessor(String operationName) {
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
     * Sets dueMax
     * 
     * @param value Value to set
     */
    public void setDueMax(Object value) {
        this.dueMax = value;
    }

    /**
     * Sets showHidden
     * 
     * @param value Value to set
     */
    public void setShowHidden(Object value) {
        this.showHidden = value;
    }

    /**
     * Sets taskListId
     * 
     * @param value Value to set
     */
    public void setTaskListId(Object value) {
        this.taskListId = value;
    }

    /**
     * Sets completedMax
     * 
     * @param value Value to set
     */
    public void setCompletedMax(Object value) {
        this.completedMax = value;
    }

    /**
     * Sets completedMin
     * 
     * @param value Value to set
     */
    public void setCompletedMin(Object value) {
        this.completedMin = value;
    }

    /**
     * Sets showcompleted
     * 
     * @param value Value to set
     */
    public void setShowcompleted(Object value) {
        this.showcompleted = value;
    }

    /**
     * Sets dueMin
     * 
     * @param value Value to set
     */
    public void setDueMin(Object value) {
        this.dueMin = value;
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
            moduleObject = findOrCreate(GoogleTasksConnectorOAuthManager.class, false, event);
            final String _transformedTaskListId = ((String) evaluateAndTransform(getMuleContext(), event, GetTasksMessageProcessor.class.getDeclaredField("_taskListIdType").getGenericType(), null, taskListId));
            final String _transformedCompletedMin = ((String) evaluateAndTransform(getMuleContext(), event, GetTasksMessageProcessor.class.getDeclaredField("_completedMinType").getGenericType(), null, completedMin));
            final String _transformedCompletedMax = ((String) evaluateAndTransform(getMuleContext(), event, GetTasksMessageProcessor.class.getDeclaredField("_completedMaxType").getGenericType(), null, completedMax));
            final String _transformedDueMin = ((String) evaluateAndTransform(getMuleContext(), event, GetTasksMessageProcessor.class.getDeclaredField("_dueMinType").getGenericType(), null, dueMin));
            final String _transformedDueMax = ((String) evaluateAndTransform(getMuleContext(), event, GetTasksMessageProcessor.class.getDeclaredField("_dueMaxType").getGenericType(), null, dueMax));
            final String _transformedUpdatedMin = ((String) evaluateAndTransform(getMuleContext(), event, GetTasksMessageProcessor.class.getDeclaredField("_updatedMinType").getGenericType(), null, updatedMin));
            final Boolean _transformedShowDeleted = ((Boolean) evaluateAndTransform(getMuleContext(), event, GetTasksMessageProcessor.class.getDeclaredField("_showDeletedType").getGenericType(), null, showDeleted));
            final Boolean _transformedShowHidden = ((Boolean) evaluateAndTransform(getMuleContext(), event, GetTasksMessageProcessor.class.getDeclaredField("_showHiddenType").getGenericType(), null, showHidden));
            final Boolean _transformedShowcompleted = ((Boolean) evaluateAndTransform(getMuleContext(), event, GetTasksMessageProcessor.class.getDeclaredField("_showcompletedType").getGenericType(), null, showcompleted));
            final PagingConfiguration _transformedPagingConfiguration = ((PagingConfiguration) evaluateAndTransform(getMuleContext(), event, GetTasksMessageProcessor.class.getDeclaredField("_pagingConfigurationType").getGenericType(), null, pagingConfiguration));
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
                    return ((GoogleTasksConnector) object).getTasks(_transformedTaskListId, _transformedCompletedMin, _transformedCompletedMax, _transformedDueMin, _transformedDueMax, _transformedUpdatedMin, _transformedShowDeleted, _transformedShowHidden, _transformedShowcompleted, _transformedPagingConfiguration);
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
        return new DefaultResult<MetaData>(new DefaultMetaData(new DefaultListMetaDataModel(getPojoOrSimpleModel(Task.class))));
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
            connector = ((ConnectorMetaDataEnabled) findOrCreate(GoogleTasksConnector.class, true, null));
            try {
                Result<MetaData> metadata = connector.getMetaData(metaDataKey);
                if ((Result.Status.FAILURE).equals(metadata.getStatus())) {
                    return metadata;
                }
                if (metadata.get() == null) {
                    return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error processing metadata at GoogleTasksConnector at getTasks retrieving was successful but result is null");
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
