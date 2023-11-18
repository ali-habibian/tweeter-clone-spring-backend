package com.habibian.tweeterclone.exception;

/**
 * Custom exception class for user-related exceptions.
 * Thrown when an error occurs during user operations, such as signup.
 */
public class UserException extends Exception {

    /**
     * Constructs a UserException with the specified error message.
     *
     * @param message The error message.
     */
    public UserException(String message) {
        super(message);
    }
}
