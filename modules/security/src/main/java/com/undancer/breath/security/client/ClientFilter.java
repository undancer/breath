package com.undancer.breath.security.client;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.common.message.types.TokenType;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static org.apache.commons.lang3.ArrayUtils.toArray;
import static org.apache.shiro.web.util.WebUtils.toHttp;

/**
 * Created by undancer on 14-4-17.
 */
@Named("clientFilter")
public class ClientFilter extends AuthenticatingFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientFilter.class);

    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        LOGGER.debug("create-token");
        try {
            String accessToken =
                    new OAuthAccessResourceRequest(
                            toHttp(request),
                            toArray(TokenType.BEARER),
                            toArray(ParameterStyle.QUERY)
                    ).getAccessToken();
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
        try {
            HttpServletResponse httpServletResponse = toHttp(response);
            OAuthResponse.OAuthErrorResponseBuilder builder = OAuthResponse.errorResponse(401);
            if (e.getCause() != null && e.getCause() instanceof OAuthProblemException) {
                builder.error((OAuthProblemException) e.getCause()).buildJSONMessage();
            } else {
                builder.setError(e.getLocalizedMessage());
            }
            OAuthResponse oAuthResponse = builder.buildJSONMessage();

            httpServletResponse.setStatus(oAuthResponse.getResponseStatus());
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            httpServletResponse.setCharacterEncoding("UTF-8");

            try (PrintWriter out = httpServletResponse.getWriter()) {
                out.println(oAuthResponse.getBody());
            }
        } catch (Exception other) {
        }
        return false;
    }
}