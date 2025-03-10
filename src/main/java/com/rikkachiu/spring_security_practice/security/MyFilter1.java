package com.rikkachiu.spring_security_practice.security;

import jakarta.servlet.*;

import java.io.IOException;

public class MyFilter1 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("filter1");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
