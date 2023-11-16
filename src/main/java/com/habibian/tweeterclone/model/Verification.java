package com.habibian.tweeterclone.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Represents the verification information associated with a user in the application.
 */
@Data
public class Verification {

    /**
     * The verification status (true if verified, false otherwise).
     */
    private boolean status = false;

    /**
     * The timestamp when the verification process started.
     */
    private LocalDateTime startedAt;

    /**
     * The timestamp when the verification process ends.
     */
    private LocalDateTime endsAt;

    /**
     * The type of verification plan (if applicable).
     */
    private String planType;
}
