package com.undancer.breath.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceView;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by undancer on 14-4-25.
 */
@Named
public class ExceptionResolver extends AbstractHandlerExceptionResolver {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionResolver.class);

    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {

        if (e instanceof ViewNotFoundException) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ModelAndView(new InternalResourceView("/404"));
        }

        return null;
    }
}
