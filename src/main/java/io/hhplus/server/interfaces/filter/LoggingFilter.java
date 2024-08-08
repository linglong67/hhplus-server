package io.hhplus.server.interfaces.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Component
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

        loggingRequest(wrappedRequest);

        long startTime = System.currentTimeMillis();
        chain.doFilter(wrappedRequest, wrappedResponse);
        long endTime = System.currentTimeMillis();

        log.info("Request processing time: {}ms", endTime - startTime);
        loggingResponse(wrappedResponse);

        wrappedResponse.copyBodyToResponse();
    }

    private void loggingRequest(ContentCachingRequestWrapper req) {
        String headers = getRequestHeaders(req);
        String body = new String(req.getContentAsByteArray());
        log.info("\n### Request [{} {}] \n### Headers: {} \n### Body: {}",
                req.getMethod(), req.getRequestURI(), headers, body);
    }

    private void loggingResponse(ContentCachingResponseWrapper res) {
        String headers = getResponseHeaders(res);
        String body = new String(res.getContentAsByteArray());
        log.info("\n### Response [{}] \n### Headers: {} \n### Body: {}",
                res.getStatus(), headers, body);
    }

    private String getRequestHeaders(HttpServletRequest req) {
        StringBuilder headers = new StringBuilder();
        req.getHeaderNames().asIterator().forEachRemaining(
                headerName -> headers.append(headerName)
                                     .append(": ")
                                     .append(req.getHeader(headerName))
                                     .append("; "));
        return headers.toString();
    }

    private String getResponseHeaders(HttpServletResponse res) {
        StringBuilder headers = new StringBuilder();
        res.getHeaderNames().forEach(
                headerName -> headers.append(headerName)
                                     .append(": ")
                                     .append(res.getHeader(headerName))
                                     .append("; ")
        );
        return headers.toString();
    }
}