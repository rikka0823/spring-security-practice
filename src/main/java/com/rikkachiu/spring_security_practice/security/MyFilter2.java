package com.rikkachiu.spring_security_practice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class MyFilter2 extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("filter2");

        System.out.println(request.getRequestURI());

        if (!request.getRequestURI().equals("/getMovies")) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write("400");
        }

        if (request.getRequestURI().equals("/getMovies")) {
            filterChain.doFilter(request, response);
        }


    }
}
