package com.example.coreMack.filter;

import com.example.coreMack.util.ClientCorrelationIdContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "SetCorrelationId")
@Component
public class SetCorrelationId implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        String correlationId = request1.getHeader("correlation-id");
        try{
            if (correlationId!=null)
            ClientCorrelationIdContextHolder.setCorrelationId(correlationId);
            chain.doFilter(request, response);
        }finally {
            ClientCorrelationIdContextHolder.clearContext();
        }
    }
}
