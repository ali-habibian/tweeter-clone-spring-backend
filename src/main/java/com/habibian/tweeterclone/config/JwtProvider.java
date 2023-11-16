package com.habibian.tweeterclone.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utility class for generating and extracting information from JWT tokens.
 */
public class JwtProvider {
    // Secret key for signing and verifying JWT tokens
    SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    /**
     * Generates a JWT token based on the provided Authentication object.
     *
     * @param auth The Authentication object containing user details and authorities.
     * @return The generated JWT token.
     */
    public String generateToken(Authentication auth) {

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000)) // Token expiration time (24 hours)
                .claim("email", auth.getName())
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    /**
     * Extracts the email information from a JWT token.
     *
     * @param jwt The JWT token from which to extract the email.
     * @return The email extracted from the token.
     */
    public String getEmailFromToken(String jwt) {
        // Remove the "Bearer " prefix from the token
        jwt = jwt.substring(7);

        // Parse the JWT token
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();

        // extract the email claim and return it
        return String.valueOf(claims.get("email"));
    }
}
