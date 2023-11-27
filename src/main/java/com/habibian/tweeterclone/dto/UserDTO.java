package com.habibian.tweeterclone.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing user information.
 */
@Data
@Builder
public class UserDTO {
    private Long id;
    private String fullname;
    private String email;
    private String image;
    private String location;
    private String website;
    private String birthDate;
    private String mobile;
    private String backgroundImage;
    private String bio;
    private boolean req_user;
    private boolean login_with_google;
    private List<UserDTO> followers = new ArrayList<>();
    private List<UserDTO> followings = new ArrayList<>();
    private boolean followed;
    private boolean isVerified;
}
