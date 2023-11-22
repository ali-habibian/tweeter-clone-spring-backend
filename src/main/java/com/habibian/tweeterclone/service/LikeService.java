package com.habibian.tweeterclone.service;

import com.habibian.tweeterclone.exception.TweetException;
import com.habibian.tweeterclone.exception.UserException;
import com.habibian.tweeterclone.model.Like;
import com.habibian.tweeterclone.model.User;

import java.util.List;

/**
 * Service interface for managing likes on tweets.
 */
public interface LikeService {

    /**
     * Likes a tweet.
     *
     * @param tweetId The ID of the tweet to like.
     * @param user    The user liking the tweet.
     * @return The like object representing the like on the tweet.
     * @throws UserException  If the user is not valid.
     * @throws TweetException If the tweet is not found.
     */
    Like likeTweet(Long tweetId, User user) throws UserException, TweetException;

    /**
     * Retrieves all likes for a specific tweet.
     *
     * @param tweetId The ID of the tweet for which to retrieve likes.
     * @return List of likes on the specified tweet.
     * @throws TweetException If the tweet is not found.
     */
    List<Like> getAllLikes(Long tweetId) throws TweetException;
}
