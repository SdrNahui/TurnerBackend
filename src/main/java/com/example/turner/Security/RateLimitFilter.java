package com.example.turner.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitFilter extends OncePerRequestFilter {
    private final Map<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
    private final Map<String, Long> windowStart = new ConcurrentHashMap<>();
    private static final int MAX_REQUESTS = 100;
    private static final long WINDOW_MS = 60_000;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String ip = request.getRemoteAddr();
        long now = System.currentTimeMillis();
        windowStart.putIfAbsent(ip, now);
        requestCounts.putIfAbsent(ip, new AtomicInteger(0));
        if(now - windowStart.get(ip) > WINDOW_MS) {
            windowStart.put(ip, now);
            requestCounts.get(ip).set(0);
        }
        int count = requestCounts.get(ip).incrementAndGet();
        if(count > MAX_REQUESTS) {
            response.setStatus(429);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Demasiadas peticiones, intente más tarde\"}");
            long remainingMs = WINDOW_MS - (now - windowStart.get(ip));
            response.setHeader("Retry-After", String.valueOf(remainingMs / 1000));
            return;
        }
        filterChain.doFilter(request, response);
    }
}
