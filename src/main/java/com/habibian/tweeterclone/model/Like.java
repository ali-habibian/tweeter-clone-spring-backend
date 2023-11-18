package com.habibian.tweeterclone.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a like in the application.
 */
@Getter
@Setter
@Entity
@Table(name = "likes")
public class Like {
    /**
     * The unique identifier for the like.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The user who gave the like.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The tweet that received the like.
     */
    @ManyToOne
    @JoinColumn(name = "tweet_id")
    private Tweet tweet;
}
