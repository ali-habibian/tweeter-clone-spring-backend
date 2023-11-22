package com.habibian.tweeterclone.request;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * A request class representing the data required to create a reply to a tweet.
 */
@Data
public class TweetReplyRequest {

    /**
     * The content of the reply tweet.
     */
    private String content;

    /**
     * The ID of the tweet to which the reply is created.
     */
    private Long tweetId;

    /**
     * The creation timestamp of the reply tweet.
     */
    private LocalDateTime createdAt;

    /**
     * The image associated with the reply tweet.
     */
    private String image;
}
