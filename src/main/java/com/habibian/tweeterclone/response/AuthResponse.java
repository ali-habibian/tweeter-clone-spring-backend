package com.habibian.tweeterclone.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data class representing the response after user authentication.
 * Contains a JSON Web Token (JWT) and a status indicating the success of the authentication process.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    /**
     * The JSON Web Token (JWT) generated for authentication.
     */
    private String jwt;

    /**
     * The status indicating the success of the authentication process.
     */
    private boolean status;
}
