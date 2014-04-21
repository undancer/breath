package com.undancer.breath.samples.showcase.security.client;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.HostAuthenticationToken;

/**
 * Created by undancer on 14-4-17.
 */
public class ClientToken implements AuthenticationToken, HostAuthenticationToken {

    private String clientId;
    private String accessToken;
    private String host;

    public ClientToken(String accessToken) {
        this(accessToken, null);
    }

    public ClientToken(String accessToken, String host) {
        this.accessToken = accessToken;
        this.host = host;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Object getPrincipal() {
        return clientId;
    }

    public Object getCredentials() {
        return accessToken;
    }
}
