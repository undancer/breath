package com.undancer.breath.core.filter;

import com.google.common.base.Stopwatch;
import com.undancer.breath.core.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by undancer on 14-4-16.
 */
public class RequestFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestFilter.class);

    private static final NamedThreadLocal<HttpServletRequest> requestHolder = new NamedThreadLocal<>("request");

    public static HttpServletRequest getRequest() {
        return requestHolder.get();
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        requestHolder.set(request);

        beforeRequest(request, response);

        chain.doFilter(request, response);

        afterRequest(request, response);

        requestHolder.set(null);
    }

    private void beforeRequest(HttpServletRequest request, HttpServletResponse response) {
        if (LOGGER.isDebugEnabled()) {
            RequestUtils.setAttribute(getClass() + ".STOPWATCH", Stopwatch.createStarted());
        }
    }

    private void afterRequest(HttpServletRequest request, HttpServletResponse response) {
        if (LOGGER.isDebugEnabled()) {
            Stopwatch timer = RequestUtils.getAttribute(getClass() + ".STOPWATCH");
            if (timer != null && timer.isRunning()) {
                timer.stop();
                String url = RequestUtils.getUrl();
                String method = RequestUtils.getMethod().toUpperCase();
                LOGGER.debug("[BREATH/CORE][{}] {} {} ms.", method, url, timer.elapsed(TimeUnit.MILLISECONDS));
            }
        }
    }
}
