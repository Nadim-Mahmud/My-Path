package com.wheelchair.mypath.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.util.Objects.isNull;

/**
 * @author Nadim Mahmud
 * @date 2/27/25
 */

@Component
public class ApiKeyFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(ApiKeyFilter.class);

    @Value("${api.key}")
    private String validApiKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        if (requestURI.startsWith("/route/")) {
            String apiKey = httpRequest.getHeader("API_KEY");

            if (isNull(apiKey) || !apiKey.equals(validApiKey)) {
                logger.warn("Invalid or missing API key for request to {}", requestURI);

                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
                httpResponse.getWriter().write("Invalid API Key");

                return;
            }
        }

        chain.doFilter(request, response);
    }
}