package com.coffee.tracker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider tokenProvider; private final UserDetailsService userDetailsService;
    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, UserDetailsService userDetailsService){ this.tokenProvider=tokenProvider; this.userDetailsService=userDetailsService; }
    @Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException { String header=request.getHeader(HttpHeaders.AUTHORIZATION); if(header!=null && header.startsWith("Bearer ")){ String token=header.substring(7); if(tokenProvider.isValid(token)){ Long userId= tokenProvider.getUserId(token); UserDetails details = userDetailsService.loadUserByUsername(String.valueOf(userId)); UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(details,null,details.getAuthorities()); auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); SecurityContextHolder.getContext().setAuthentication(auth);} } chain.doFilter(request,response);} }
