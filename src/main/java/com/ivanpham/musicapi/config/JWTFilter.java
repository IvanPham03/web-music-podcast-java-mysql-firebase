package com.ivanpham.musicapi.config;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@NoArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String token = resolveToken(request);
            if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getUserEmailFromToken(token);

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        } catch (AuthenticationCredentialsNotFoundException exception) {
            response.setHeader("Message", exception.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
        }

    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}