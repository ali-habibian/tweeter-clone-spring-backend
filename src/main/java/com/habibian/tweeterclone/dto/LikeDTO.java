package com.habibian.tweeterclone.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) representing like information.
 */
@Data
@Builder
public class LikeDTO {
    private Long id;
    private UserDTO user;
    private TweetDTO tweet;
}
