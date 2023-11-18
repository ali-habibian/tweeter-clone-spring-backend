package com.habibian.tweeterclone.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

/**
 * Custom filter for validating and processing JWT tokens in the incoming requests.
 */
public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Extract JWT token from the request header
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        if (jwt != null) {
            // Remove the "Bearer " prefix from the token
            jwt = jwt.substring(7);

            try {
                // Validate and parse the JWT token
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();

                // Extract user details and authorities from the token
                String email = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("authorities"));
                List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                // Create an Authentication object and set it in the SecurityContext
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // Invalid token handling
                throw new BadCredentialsException("Invalid token...");
            }
        }
        // Continue with the filter chain after successful token validation
        filterChain.doFilter(request, response);
    }
}
