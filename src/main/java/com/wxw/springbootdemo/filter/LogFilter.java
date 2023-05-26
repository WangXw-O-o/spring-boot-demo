package com.wxw.springbootdemo.filter;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@WebFilter(filterName = "logFilter", urlPatterns = "/*")
public class LogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            MDC.put("traceId", UUID.randomUUID().toString().replaceAll("-", ""));
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            log.error("filter error :", e);
            throw new RuntimeException(e);
        } finally {
            MDC.clear();
        }

    }
}
