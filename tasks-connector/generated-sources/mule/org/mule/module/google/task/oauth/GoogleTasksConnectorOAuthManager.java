
package org.mule.module.google.task.oauth;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.expression.ExpressionManager;
import org.mule.api.store.ObjectStore;
import org.mule.module.google.task.GoogleTasksConnector;
import org.mule.module.google.task.adapters.GoogleTasksConnectorOAuth2Adapter;
import org.mule.security.oauth.BaseOAuth2Manager;
import org.mule.security.oauth.OAuth2Adapter;
import org.mule.security.oauth.OAuth2Manager;
import org.mule.security.oauth.OnNoTokenPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A {@code GoogleTasksConnectorOAuthManager} is a wrapper around {@link GoogleTasksConnector } that adds access token management capabilities to the pojo.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T11:47:49-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class GoogleTasksConnectorOAuthManager
    extends BaseOAuth2Manager<OAuth2Adapter>
{

    private static Logger logger = LoggerFactory.getLogger(GoogleTasksConnectorOAuthManager.class);
    private final static String MODULE_NAME = "Google Tasks";
    private final static String MODULE_VERSION = "1.2.4-SNAPSHOT";
    private final static String DEVKIT_VERSION = "3.5.0-SNAPSHOT";
    private final static String DEVKIT_BUILD = "UNKNOWN_BUILDNUMBER";

    @Override
    protected Logger getLogger() {
        return logger;
    }

    /**
     * Sets consumerKey
     * 
     * @param key to set
     */
    public void setConsumerKey(String value) {
        super.setConsumerKey(value);
    }

    /**
     * Sets consumerSecret
     * 
     * @param secret to set
     */
    public void setConsumerSecret(String value) {
        super.setConsumerSecret(value);
    }

    /**
     * Sets scope
     * 
     * @param scope to set
     */
    public void setScope(String value) {
        super.setScope(value);
    }

    /**
     * Sets applicationName
     * 
     * @param scope to set
     */
    public void setApplicationName(String value) {
        GoogleTasksConnectorOAuth2Adapter connector = ((GoogleTasksConnectorOAuth2Adapter) this.getDefaultUnauthorizedConnector());
        connector.setApplicationName(value);
    }

    /**
     * Retrieves applicationName
     * 
     */
    public String getApplicationName() {
        GoogleTasksConnectorOAuth2Adapter connector = ((GoogleTasksConnectorOAuth2Adapter) this.getDefaultUnauthorizedConnector());
        return connector.getApplicationName();
    }

    public String getModuleName() {
        return MODULE_NAME;
    }

    public String getModuleVersion() {
        return MODULE_VERSION;
    }

    public String getDevkitVersion() {
        return DEVKIT_VERSION;
    }

    public String getDevkitBuild() {
        return DEVKIT_BUILD;
    }

    @Override
    protected OAuth2Adapter instantiateAdapter() {
        return new GoogleTasksConnectorOAuth2Adapter(this);
    }

    @Override
    protected KeyedPoolableObjectFactory createPoolFactory(OAuth2Manager<OAuth2Adapter> oauthManager, ObjectStore<Serializable> objectStore) {
        return new GoogleTasksConnectorOAuthClientFactory(oauthManager, objectStore);
    }

    @Override
    protected void setCustomProperties(OAuth2Adapter adapter) {
        GoogleTasksConnectorOAuth2Adapter connector = ((GoogleTasksConnectorOAuth2Adapter) adapter);
        connector.setConsumerKey(getConsumerKey());
        connector.setConsumerSecret(getConsumerSecret());
        connector.setScope(getScope());
        connector.setApplicationName(getApplicationName());
    }

    protected void fetchCallbackParameters(OAuth2Adapter adapter, String response) {
        GoogleTasksConnectorOAuth2Adapter connector = ((GoogleTasksConnectorOAuth2Adapter) adapter);
        ExpressionManager expressionManager = (muleContext.getExpressionManager());
        MuleMessage muleMessage = new DefaultMuleMessage(response, (muleContext));
    }

    public void setOnNoToken(OnNoTokenPolicy policy) {
        this.getDefaultUnauthorizedConnector().setOnNoTokenPolicy(policy);
    }

    @Override
    protected Set<Class<? extends Exception>> refreshAccessTokenOn() {
        Set<Class<? extends Exception>> types = new HashSet<Class<? extends Exception>>();
        types.add((org.mule.modules.google.oauth.invalidation.OAuthTokenExpiredException.class));
        return types;
    }

}
