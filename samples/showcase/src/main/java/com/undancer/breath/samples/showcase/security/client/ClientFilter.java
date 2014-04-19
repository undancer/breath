package com.undancer.breath.samples.showcase.security.client;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by undancer on 14-4-17.
 */
@Named("clientFilter")
public class ClientFilter extends AuthenticatingFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientFilter.class);

    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        LOGGER.debug("create-token");
        try {
            String accessToken = new OAuthAccessResourceRequest(WebUtils.toHttp(request)).getAccessToken();
            return new ClientToken(accessToken, getHost(request));
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return executeLogin(request, response);
    }

    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        try {
            return super.executeLogin(request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(null, e, request, response);
        }
    }

    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        Throwable throwable = e.getCause();
        if (throwable instanceof OAuthProblemException) {
            OAuthProblemException problem = (OAuthProblemException) throwable;
            try {
                HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
                OAuthResponse oAuthResponse = OAuthResponse.errorResponse(401).error(problem).buildJSONMessage();

                httpServletResponse.setStatus(oAuthResponse.getResponseStatus());
                try (ServletOutputStream out = httpServletResponse.getOutputStream()) {
                    out.println(oAuthResponse.getBody());
                }
            } catch (Exception other) {

            }
        }
        return false;
    }
}