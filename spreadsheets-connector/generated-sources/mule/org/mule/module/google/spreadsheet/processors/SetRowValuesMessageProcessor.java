
package org.mule.module.google.spreadsheet.processors;

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
import org.mule.module.google.spreadsheet.GoogleSpreadSheetConnector;
import org.mule.module.google.spreadsheet.model.Row;
import org.mule.module.google.spreadsheet.oauth.GoogleSpreadSheetConnectorOAuthManager;
import org.mule.modules.google.oauth.invalidation.OAuthTokenExpiredException;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * SetRowValuesMessageProcessor invokes the {@link org.mule.module.google.spreadsheet.GoogleSpreadSheetConnector#setRowValues(java.util.List, java.lang.String, java.lang.String, int, int, boolean)} method in {@link GoogleSpreadSheetConnector }. For each argument there is a field in this processor to match it.  Before invoking the actual method the processor will evaluate and transform where possible to the expected argument type.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T11:47:11-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class SetRowValuesMessageProcessor
    extends AbstractConnectedProcessor
    implements MessageProcessor, OperationMetaDataEnabled
{

    protected Object rows;
    protected List<Row> _rowsType;
    protected Object spreadsheet;
    protected String _spreadsheetType;
    protected Object worksheet;
    protected String _worksheetType;
    protected Object spreadsheetIndex;
    protected int _spreadsheetIndexType;
    protected Object worksheetIndex;
    protected int _worksheetIndexType;
    protected Object purge;
    protected boolean _purgeType;

    public SetRowValuesMessageProcessor(String operationName) {
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
     * Sets worksheetIndex
     * 
     * @param value Value to set
     */
    public void setWorksheetIndex(Object value) {
        this.worksheetIndex = value;
    }

    /**
     * Sets spreadsheetIndex
     * 
     * @param value Value to set
     */
    public void setSpreadsheetIndex(Object value) {
        this.spreadsheetIndex = value;
    }

    /**
     * Sets worksheet
     * 
     * @param value Value to set
     */
    public void setWorksheet(Object value) {
        this.worksheet = value;
    }

    /**
     * Sets spreadsheet
     * 
     * @param value Value to set
     */
    public void setSpreadsheet(Object value) {
        this.spreadsheet = value;
    }

    /**
     * Sets purge
     * 
     * @param value Value to set
     */
    public void setPurge(Object value) {
        this.purge = value;
    }

    /**
     * Sets rows
     * 
     * @param value Value to set
     */
    public void setRows(Object value) {
        this.rows = value;
    }

    /**
     * Invokes the MessageProcessor.
     * 
     * @param event MuleEvent to be processed
     * @throws Exception
     */
    public MuleEvent doProcess(final MuleEvent event)
        throws Exception
    {
        Object moduleObject = null;
        try {
            moduleObject = findOrCreate(GoogleSpreadSheetConnectorOAuthManager.class, false, event);
            final List<Row> _transformedRows = ((List<Row> ) evaluateAndTransform(getMuleContext(), event, SetRowValuesMessageProcessor.class.getDeclaredField("_rowsType").getGenericType(), null, rows));
            final String _transformedSpreadsheet = ((String) evaluateAndTransform(getMuleContext(), event, SetRowValuesMessageProcessor.class.getDeclaredField("_spreadsheetType").getGenericType(), null, spreadsheet));
            final String _transformedWorksheet = ((String) evaluateAndTransform(getMuleContext(), event, SetRowValuesMessageProcessor.class.getDeclaredField("_worksheetType").getGenericType(), null, worksheet));
            final Integer _transformedSpreadsheetIndex = ((Integer) evaluateAndTransform(getMuleContext(), event, SetRowValuesMessageProcessor.class.getDeclaredField("_spreadsheetIndexType").getGenericType(), null, spreadsheetIndex));
            final Integer _transformedWorksheetIndex = ((Integer) evaluateAndTransform(getMuleContext(), event, SetRowValuesMessageProcessor.class.getDeclaredField("_worksheetIndexType").getGenericType(), null, worksheetIndex));
            final Boolean _transformedPurge = ((Boolean) evaluateAndTransform(getMuleContext(), event, SetRowValuesMessageProcessor.class.getDeclaredField("_purgeType").getGenericType(), null, purge));
            ProcessTemplate<Object, Object> processTemplate = ((ProcessAdapter<Object> ) moduleObject).getProcessTemplate();
            processTemplate.execute(new ProcessCallback<Object,Object>() {


                public List<Class<? extends Exception>> getManagedExceptions() {
                    return Arrays.asList(((Class<? extends Exception> []) new Class[] {OAuthTokenExpiredException.class }));
                }

                public boolean isProtected() {
                    return true;
                }

                public Object process(Object object)
                    throws Exception
                {
                    ((GoogleSpreadSheetConnector) object).setRowValues(_transformedRows, _transformedSpreadsheet, _transformedWorksheet, _transformedSpreadsheetIndex, _transformedWorksheetIndex, _transformedPurge);
                    return null;
                }

            }
            , this, event);
            return event;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Result<MetaData> getInputMetaData() {
        return new DefaultResult<MetaData>(new DefaultMetaData(new DefaultListMetaDataModel(getPojoOrSimpleModel(Row.class))));
    }

    @Override
    public Result<MetaData> getOutputMetaData(MetaData inputMetadata) {
        return new DefaultResult<MetaData>(new DefaultMetaData(getPojoOrSimpleModel(void.class)));
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
            connector = ((ConnectorMetaDataEnabled) findOrCreate(GoogleSpreadSheetConnector.class, true, null));
            try {
                Result<MetaData> metadata = connector.getMetaData(metaDataKey);
                if ((Result.Status.FAILURE).equals(metadata.getStatus())) {
                    return metadata;
                }
                if (metadata.get() == null) {
                    return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), "There was an error processing metadata at GoogleSpreadSheetConnector at setRowValues retrieving was successful but result is null");
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
