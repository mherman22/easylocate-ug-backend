package com.easylocate.filter;

import com.easylocate.service.JWTService;
import com.easylocate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter for processing JSON Web Tokens (JWT) in HTTP requests.
 * <p>
 * This filter ensures that the JWT is validated and the user details are extracted
 * for authentication within the application.
 * </p>
 * Extends {@link OncePerRequestFilter}, ensuring the filter is executed once per request.
 */
@Configuration
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    @SuppressWarnings("unused")
    private JWTService jwtService;

    @Autowired
    @SuppressWarnings("unused")
    private ApplicationContext context;

    /**
     * Filters incoming HTTP requests to validate JWTs and authenticate users based on the token.
     *
     * @param request     the {@link HttpServletRequest} for the incoming request
     * @param response    the {@link HttpServletResponse} for the outgoing response
     * @param filterChain the {@link FilterChain} to pass the request and response to the next filter
     * @throws ServletException if an error occurs during the filtering process
     * @throws IOException      if an I/O error occurs during the filtering process
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authenticationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
            token = authenticationHeader.substring(7);
            username = jwtService.extractUsernameFromJWToken(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = context.getBean(UserService.class).loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
