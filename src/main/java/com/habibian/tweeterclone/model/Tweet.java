package com.habibian.tweeterclone.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a tweet in the TweeterClone application.
 */
@Getter
@Setter
@Entity
@Table(name = "tweets")
public class Tweet {
    /**
     * The unique identifier for the tweet.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The content of the tweet.
     */
    private String content;

    /**
     * The URL of an image attached to the tweet.
     */
    private String image;

    /**
     * The URL of a video attached to the tweet.
     */
    private String video;

    /**
     * The user who posted the tweet.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The list of likes associated with the tweet.
     */
    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    /**
     * The list of reply tweets associated with the tweet.
     */
    @OneToMany
    private List<Tweet> replyTweets = new ArrayList<>();

    /**
     * The list of users who retweeted the tweet.
     */
    @ManyToMany
    private List<User> retweetUser = new ArrayList<>();

    /**
     * The tweet to which this tweet is a reply.
     */
    @ManyToOne
    private Tweet replyFor;

    /**
     * Flag indicating if the tweet is a reply.
     */
    private Boolean isReply;

    /**
     * Flag indicating if the object is a tweet.
     */
    private Boolean isTweet;

    private LocalDateTime createdAt;
}
