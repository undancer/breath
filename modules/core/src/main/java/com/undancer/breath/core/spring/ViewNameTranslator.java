package com.undancer.breath.core.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;
import org.springframework.web.util.UrlPathHelper;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import static com.undancer.breath.core.util.RequestUtils.contextRelative;
import static org.springframework.util.StringUtils.stripFilenameExtension;

/**
 * Created by undancer on 14-4-18.
 */
@Named("viewNameTranslator")
public class ViewNameTranslator extends DefaultRequestToViewNameTranslator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewNameTranslator.class);

    UrlPathHelper urlPathHelper = new UrlPathHelper();

    public String getViewName(HttpServletRequest request) {
        String uri = stripFilenameExtension(contextRelative(request.getRequestURI().replaceAll("/+", "/"), false)).replaceAll("/$", "");
        LOGGER.debug("viewNameTranslator: uri -> {}", uri);
        String lookupPath = urlPathHelper.getLookupPathForRequest(request);
        System.err.println("lookupPath -> " + lookupPath);
        return super.getViewName(request);
    }
}
