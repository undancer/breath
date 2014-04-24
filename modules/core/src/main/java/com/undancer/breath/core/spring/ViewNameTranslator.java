package com.undancer.breath.core.spring;

import com.undancer.breath.core.util.ReflectionUtils;
import com.undancer.breath.core.util.RequestUtils;
import com.undancer.breath.core.util.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.List;

import static com.undancer.breath.core.util.RequestUtils.contextRelative;
import static org.springframework.util.StringUtils.stripFilenameExtension;

/**
 * Created by undancer on 14-4-18.
 */
@Named("viewNameTranslator")
public class ViewNameTranslator extends DefaultRequestToViewNameTranslator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewNameTranslator.class);

    private final static String INDEX = "/index";

    public String getViewName(HttpServletRequest request) {
        String uri = stripFilenameExtension(contextRelative(request.getRequestURI().replaceAll("/+", "/"), false)).replaceAll("/$", "");
        LOGGER.debug("viewNameTranslator: uri -> {}", uri);

        List<UrlBasedViewResolver> viewResolvers = ResourceUtils.getResources(UrlBasedViewResolver.class);
        for (UrlBasedViewResolver viewResolver : viewResolvers) {
            String prefix = ReflectionUtils.<String>getPrivateField(viewResolver, "prefix").replaceAll("^/", "");
            String suffix = ReflectionUtils.getPrivateField(viewResolver, "suffix");

            String viewName = findViewName(prefix, uri, suffix, false);
            if (viewName != null) {
                return viewName;
            }
        }

        return super.getViewName(request);
    }

    public String findViewName(String prefix, String path, String suffix, boolean greedy) {
        try {
            for (String lookupPath : new String[]{path, path + INDEX}) {
                lookupPath = transformPath(lookupPath);
                if (lookupPath != null && lookupPath.length() > 0) {
                    URL uri = RequestUtils.getServletContext().getResource(prefix + lookupPath + suffix);
                    if (uri != null) {
                        return lookupPath;
                    }
                    uri = ResourceUtils.getClassPathResource(prefix + lookupPath + suffix);
                    if (uri != null) {
                        return lookupPath;
                    }
                }
            }
        } catch (Exception e) {
        }

        if (greedy && path.lastIndexOf("/") != -1) {
            return findViewName(prefix, path.substring(0, path.lastIndexOf("/")), suffix, greedy);
        }
        return null;
    }
}
