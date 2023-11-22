package com.habibian.tweeterclone.exception;

/**
 * Custom exception class for handling exceptions related to tweets in the application.
 */
public class TweetException extends Exception {

    /**
     * Constructs a new TweetException with the specified detail message.
     *
     * @param message the detail message.
     */
    public TweetException(String message) {
        super(message);
    }
}
