package com.undancer.breath.core;

import org.springframework.core.NamedThreadLocal;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by undancer on 14-4-16.
 */
public class RequestFilter extends OncePerRequestFilter {

    private static final NamedThreadLocal<HttpServletRequest> requestHolder = new NamedThreadLocal<>("request");

    public static HttpServletRequest getRequest() {
        return requestHolder.get();
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        requestHolder.set(request);

        chain.doFilter(request, response);

        requestHolder.set(null);
    }
}
