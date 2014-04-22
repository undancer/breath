package com.undancer.breath.core.filter;

import com.google.common.base.Stopwatch;
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
        Stopwatch timer = null;
        String url = null, method = null;

        if (LOGGER.isDebugEnabled()) {
            timer = Stopwatch.createStarted();
            StringBuffer uri = request.getRequestURL();
            String query = request.getQueryString();
            if (query != null && query.length() > 0) {
                uri.append('?').append(query);
            }
            url = uri.toString();
            method = request.getMethod().toUpperCase();
        }

        requestHolder.set(request);

        chain.doFilter(request, response);

        requestHolder.set(null);

        if (LOGGER.isDebugEnabled()) {
            if (timer != null && timer.isRunning()) {
                timer.stop();
                LOGGER.debug("[BREATH/CORE][{}] {} {}ms.", method, url, timer.elapsed(TimeUnit.MILLISECONDS));
            }
        }
    }
}
