
package org.mule.module.google.calendar.oauth;

import java.io.Serializable;
import javax.annotation.Generated;
import org.mule.module.google.calendar.GoogleCalendarConnector;


/**
 * Serializable class used to save and restore OAuth state from {@link GoogleCalendarConnector }
 * 
 */
@Deprecated
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-18T03:26:10-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class GoogleCalendarConnectorOAuthState implements Serializable
{

    public String accessToken;
    public String authorizationUrl;
    public String accessTokenUrl;
    public String refreshToken;

    /**
     * Retrieves accessToken
     * 
     */
    public String getAccessToken() {
        return this.accessToken;
    }

    /**
     * Sets accessToken
     * 
     * @param value Value to set
     */
    public void setAccessToken(String value) {
        this.accessToken = value;
    }

    /**
     * Retrieves authorizationUrl
     * 
     */
    public String getAuthorizationUrl() {
        return this.authorizationUrl;
    }

    /**
     * Sets authorizationUrl
     * 
     * @param value Value to set
     */
    public void setAuthorizationUrl(String value) {
        this.authorizationUrl = value;
    }

    /**
     * Retrieves accessTokenUrl
     * 
     */
    public String getAccessTokenUrl() {
        return this.accessTokenUrl;
    }

    /**
     * Sets accessTokenUrl
     * 
     * @param value Value to set
     */
    public void setAccessTokenUrl(String value) {
        this.accessTokenUrl = value;
    }

    /**
     * Retrieves refreshToken
     * 
     */
    public String getRefreshToken() {
        return this.refreshToken;
    }

    /**
     * Sets refreshToken
     * 
     * @param value Value to set
     */
    public void setRefreshToken(String value) {
        this.refreshToken = value;
    }

}
