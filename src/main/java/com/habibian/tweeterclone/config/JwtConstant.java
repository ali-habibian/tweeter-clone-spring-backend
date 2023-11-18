package com.habibian.tweeterclone.config;

/**
 * Constants related to JWT authentication.
 */
public class JwtConstant {
    // Secret key for signing and verifying JWT tokens
    public static final String SECRET_KEY = "[a-zA-z0-9._]^+$Guidelines89797987forAlphabeticalArrayNumeralsAndOtherSymbol$";

    // Header key for JWT token in the HTTP request
    public static final String JWT_HEADER = "Authorization";
}
