
package org.mule.module.google.task.oauth;

import java.io.Serializable;
import javax.annotation.Generated;
import org.mule.api.store.ObjectStore;
import org.mule.common.security.oauth.OAuthState;
import org.mule.module.google.task.adapters.GoogleTasksConnectorOAuth2Adapter;
import org.mule.security.oauth.BaseOAuthClientFactory;
import org.mule.security.oauth.OAuth2Adapter;
import org.mule.security.oauth.OAuth2Manager;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-18T03:28:27-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class GoogleTasksConnectorOAuthClientFactory
    extends BaseOAuthClientFactory
{

    private GoogleTasksConnectorOAuthManager oauthManager;

    public GoogleTasksConnectorOAuthClientFactory(OAuth2Manager<OAuth2Adapter> oauthManager, ObjectStore<Serializable> objectStore) {
        super(oauthManager, objectStore);
        this.oauthManager = (GoogleTasksConnectorOAuthManager) oauthManager;
    }

    @Override
    protected Class<? extends OAuth2Adapter> getAdapterClass() {
        return GoogleTasksConnectorOAuth2Adapter.class;
    }

    @Override
    protected void setCustomAdapterProperties(OAuth2Adapter adapter, OAuthState state) {
        GoogleTasksConnectorOAuth2Adapter connector = ((GoogleTasksConnectorOAuth2Adapter) adapter);
        connector.setScope(oauthManager.getScope());
        connector.setApplicationName(oauthManager.getApplicationName());
    }

    @Override
    protected void setCustomStateProperties(OAuth2Adapter adapter, OAuthState state) {
        GoogleTasksConnectorOAuth2Adapter connector = ((GoogleTasksConnectorOAuth2Adapter) adapter);
    }

}
