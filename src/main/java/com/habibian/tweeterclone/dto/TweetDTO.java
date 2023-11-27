package com.habibian.tweeterclone.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing tweet information.
 */
@Data
@Builder
public class TweetDTO {
    private Long id;
    private String content;
    private String image;
    private String video;
    private UserDTO user;
    private LocalDateTime createdAt;
    private int totalLikes;
    private int totalReplies;
    private int totalRetweets;
    private boolean isLiked;
    private boolean isRetweet;
    private List<Long> retweetUserIds;
    private List<TweetDTO> replyTweets;
}
