
package org.mule.modules.google.contact.oauth;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.expression.ExpressionManager;
import org.mule.api.store.ObjectStore;
import org.mule.modules.google.contact.GoogleContactsConnector;
import org.mule.modules.google.contact.adapters.GoogleContactsConnectorOAuth2Adapter;
import org.mule.security.oauth.BaseOAuth2Manager;
import org.mule.security.oauth.OAuth2Adapter;
import org.mule.security.oauth.OAuth2Manager;
import org.mule.security.oauth.OnNoTokenPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A {@code GoogleContactsConnectorOAuthManager} is a wrapper around {@link GoogleContactsConnector } that adds access token management capabilities to the pojo.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-20T04:28:45-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class GoogleContactsConnectorOAuthManager
    extends BaseOAuth2Manager<OAuth2Adapter>
{

    private static Logger logger = LoggerFactory.getLogger(GoogleContactsConnectorOAuthManager.class);
    private final static String MODULE_NAME = "Google Contacts";
    private final static String MODULE_VERSION = "1.2.4-SNAPSHOT";
    private final static String DEVKIT_VERSION = "3.5.0-SNAPSHOT";
    private final static String DEVKIT_BUILD = "UNKNOWN_BUILDNUMBER";

    @Override
    protected Logger getLogger() {
        return logger;
    }

    /**
     * Sets applicationName
     * 
     * @param scope to set
     */
    public void setApplicationName(String value) {
        GoogleContactsConnectorOAuth2Adapter connector = ((GoogleContactsConnectorOAuth2Adapter) this.getDefaultUnauthorizedConnector());
        connector.setApplicationName(value);
    }

    /**
     * Retrieves applicationName
     * 
     */
    public String getApplicationName() {
        GoogleContactsConnectorOAuth2Adapter connector = ((GoogleContactsConnectorOAuth2Adapter) this.getDefaultUnauthorizedConnector());
        return connector.getApplicationName();
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
        return new GoogleContactsConnectorOAuth2Adapter(this);
    }

    @Override
    protected KeyedPoolableObjectFactory createPoolFactory(OAuth2Manager<OAuth2Adapter> oauthManager, ObjectStore<Serializable> objectStore) {
        return new GoogleContactsConnectorOAuthClientFactory(oauthManager, objectStore);
    }

    @Override
    protected void setCustomProperties(OAuth2Adapter adapter) {
        GoogleContactsConnectorOAuth2Adapter connector = ((GoogleContactsConnectorOAuth2Adapter) adapter);
        connector.setApplicationName(getApplicationName());
        connector.setConsumerKey(getConsumerKey());
        connector.setConsumerSecret(getConsumerSecret());
        connector.setScope(getScope());
    }

    protected void fetchCallbackParameters(OAuth2Adapter adapter, String response) {
        GoogleContactsConnectorOAuth2Adapter connector = ((GoogleContactsConnectorOAuth2Adapter) adapter);
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
