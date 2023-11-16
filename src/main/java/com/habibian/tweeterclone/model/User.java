package com.habibian.tweeterclone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the TweeterClone application.
 */
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The full name of the user.
     */
    private String fullname;

    /**
     * The location information of the user.
     */
    private String location;

    /**
     * The website URL associated with the user.
     */
    private String website;

    /**
     * The birthdate of the user.
     */
    private String birthDate;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The mobile phone number of the user.
     */
    private String mobile;

    /**
     * The URL of the user's profile image.
     */
    private String image;

    /**
     * The URL of the user's background image.
     */
    private String backgroundImage;

    /**
     * The biography or description of the user.
     */
    private String bio;

    /**
     * Flag indicating if the user is a requested user.
     */
    private boolean req_user;

    /**
     * Flag indicating if the user logs in with Google.
     */
    private boolean login_with_google;

    /**
     * The list of tweets associated with the user.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Tweet> tweets = new ArrayList<>();

    /**
     * The list of likes associated with the user.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    /**
     * The verification information associated with the user.
     */
    @Embedded
    private Verification verification;

    /**
     * The list of followers for the user.
     */
    @JsonIgnore
    @ManyToMany
    private List<User> followers = new ArrayList<>();

    /**
     * The list of users the user is following.
     */
    @JsonIgnore
    @ManyToMany
    private List<User> followings = new ArrayList<>();
}