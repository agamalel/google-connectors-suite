
package org.mule.modules.google.contact.oauth;

import java.io.Serializable;
import javax.annotation.Generated;
import org.mule.api.store.ObjectStore;
import org.mule.common.security.oauth.OAuthState;
import org.mule.modules.google.contact.adapters.GoogleContactsConnectorOAuth2Adapter;
import org.mule.security.oauth.BaseOAuthClientFactory;
import org.mule.security.oauth.OAuth2Adapter;
import org.mule.security.oauth.OAuth2Manager;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-20T04:28:45-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class GoogleContactsConnectorOAuthClientFactory
    extends BaseOAuthClientFactory
{

    private GoogleContactsConnectorOAuthManager oauthManager;

    public GoogleContactsConnectorOAuthClientFactory(OAuth2Manager<OAuth2Adapter> oauthManager, ObjectStore<Serializable> objectStore) {
        super(oauthManager, objectStore);
        this.oauthManager = (GoogleContactsConnectorOAuthManager) oauthManager;
    }

    @Override
    protected Class<? extends OAuth2Adapter> getAdapterClass() {
        return GoogleContactsConnectorOAuth2Adapter.class;
    }

    @Override
    protected void setCustomAdapterProperties(OAuth2Adapter adapter, OAuthState state) {
        GoogleContactsConnectorOAuth2Adapter connector = ((GoogleContactsConnectorOAuth2Adapter) adapter);
        connector.setApplicationName(oauthManager.getApplicationName());
        connector.setScope(oauthManager.getScope());
    }

    @Override
    protected void setCustomStateProperties(OAuth2Adapter adapter, OAuthState state) {
        GoogleContactsConnectorOAuth2Adapter connector = ((GoogleContactsConnectorOAuth2Adapter) adapter);
    }

}
