package com.undancer.breath.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by undancer on 14-4-25.
 */
@Named
public class ExceptionResolver implements HandlerExceptionResolver {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionResolver.class);

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {

        if (e instanceof ViewNotFoundException) {
            //return new ModelAndView("forward:/error");
            return new ModelAndView(new InternalResourceView("/404"));
        }

        return null;
    }
}
