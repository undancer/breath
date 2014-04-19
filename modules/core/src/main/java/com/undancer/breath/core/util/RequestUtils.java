package com.undancer.breath.core.util;

import com.undancer.breath.core.RequestFilter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by undancer on 14-4-16.
 */
public class RequestUtils implements ApplicationContextAware {

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

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RequestUtils.applicationContext = applicationContext;
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
}
