package com.xk.achievement.configuration;

import com.xk.achievement.dto.UserDTO;
import com.xk.achievement.service.AuthServiceClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtSessionFilter extends OncePerRequestFilter {

    private final AuthServiceClient authServiceClient;

    public JwtSessionFilter(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            String token = (String) session.getAttribute("JWT_TOKEN");
            if (token != null) {
                try {
                    UserDTO user = authServiceClient.getUserMe(token);
                    if (user != null) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                user, null, null); // Use UserDTO as principal
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (Exception e) {
                    // Invalid token or service down, ignore and proceed (unauthenticated)
                    session.removeAttribute("JWT_TOKEN");
                }
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
