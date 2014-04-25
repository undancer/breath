package com.undancer.breath.core.util;

import com.undancer.breath.core.filter.RequestFilter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by undancer on 14-4-16.
 */
public class RequestUtils implements ApplicationContextAware {

    private static final String QUERY_STRING_SEPARATOR = "?";
    private static final String PARAMETER_SEPARATOR = "&";
    private static final String NAME_VALUE_SEPARATOR = "=";

    private static ApplicationContext applicationContext;

    public static HttpServletRequest getRequest() {
        return RequestFilter.getRequest();
    }

    public static String getStringParameter(String name) {
        try {
            return ServletRequestUtils.getStringParameter(getRequest(), name);
        } catch (ServletRequestBindingException e) {
            return null;
        }
    }

    public static String getRequestUri() {
        String requestUri = getAttribute(WebUtils.FORWARD_REQUEST_URI_ATTRIBUTE);
        if (requestUri != null) {
            return requestUri;
        }
        return getRequest().getRequestURI();
    }

    public static String getQueryString() {
        String requestUri = getAttribute(WebUtils.FORWARD_REQUEST_URI_ATTRIBUTE);
        if (requestUri != null) {
            return getAttribute(WebUtils.FORWARD_QUERY_STRING_ATTRIBUTE);
        }
        return getRequest().getQueryString();
    }

    public static String getUrl() {
        return getRequestUri() + (getQueryString() != null ? QUERY_STRING_SEPARATOR + getQueryString() : "");
    }

    public static String getMethod() {
        return getRequest().getMethod();
    }

    public static boolean isGetRequest() {
        return "GET".equalsIgnoreCase(getMethod());
    }


    public static boolean isPostRequest() {
        return "POST".equalsIgnoreCase(getMethod());
    }

    public static boolean isAjaxRequest() {
        return "XMLHttpRequest".equalsIgnoreCase(getHeader("X-Requested-With"));
    }

    public static String getHeader(String name) {
        return getRequest().getHeader(name);
    }

    public static void setAttribute(String name, Object o) {
        getRequest().setAttribute(name, o);
    }

    public static <T> T getAttribute(String name) {
        return (T) getRequest().getAttribute(name);
    }

    public static ServletContext getServletContext() {
        return getRequest().getServletContext();
    }

    public static String contextRelative(String uri, boolean contextRelative) {
        if (uri != null && uri.startsWith("/")) {
            String contextPath = getRequest().getContextPath();
            uri = uri.replaceFirst("^" + contextPath + "/?", "/");
            if (contextRelative) {
                uri = contextPath.concat(uri);
            }
        }
        return uri;
    }


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RequestUtils.applicationContext = applicationContext;
    }
}
