package com.habibian.tweeterclone.service;

import com.habibian.tweeterclone.exception.TweetException;
import com.habibian.tweeterclone.exception.UserException;
import com.habibian.tweeterclone.model.Tweet;
import com.habibian.tweeterclone.model.User;
import com.habibian.tweeterclone.request.TweetReplyRequest;

import java.util.List;

/**
 * Service interface for managing Tweet-related operations.
 */
public interface TweetService {

    /**
     * Creates a new tweet.
     *
     * @param req  The tweet request containing the tweet content.
     * @param user The user creating the tweet.
     * @return The created tweet.
     * @throws UserException If the user is not valid.
     */
    Tweet createTweet(Tweet req, User user) throws UserException;

    /**
     * Retrieves all tweets.
     *
     * @return List of all tweets.
     */
    List<Tweet> findAllTweets();

    /**
     * Retweets a tweet.
     *
     * @param tweetId The ID of the tweet to retweet.
     * @param user    The user retweeting the tweet.
     * @return The retweeted tweet.
     * @throws UserException  If the user is not valid.
     * @throws TweetException If the tweet is not found.
     */
    Tweet retweet(Long tweetId, User user) throws UserException, TweetException;

    /**
     * Retrieves a tweet by its ID.
     *
     * @param tweetId The ID of the tweet to retrieve.
     * @return The tweet with the specified ID.
     * @throws TweetException If the tweet is not found.
     */
    Tweet finById(Long tweetId) throws TweetException;

    /**
     * Deletes a tweet by its ID.
     *
     * @param tweetId The ID of the tweet to delete.
     * @param userId  The ID of the user deleting the tweet.
     * @throws UserException  If the user is not valid.
     * @throws TweetException If the tweet is not found.
     */
    void deleteByTweetId(Long tweetId, Long userId) throws UserException, TweetException;

    /**
     * Removes a user from a tweet (unlike or unretweet).
     *
     * @param tweetId The ID of the tweet to remove the user from.
     * @param user    The user to be removed from the tweet.
     * @return The modified tweet.
     * @throws UserException  If the user is not valid.
     * @throws TweetException If the tweet is not found.
     */
    Tweet removeFromTweet(Long tweetId, User user) throws UserException, TweetException;

    /**
     * Creates a reply to a tweet.
     *
     * @param req  The tweet reply request containing the reply content.
     * @param user The user creating the reply.
     * @return The created reply tweet.
     * @throws UserException  If the user is not valid.
     * @throws TweetException If the original tweet is not found.
     */
    Tweet createReply(TweetReplyRequest req, User user) throws UserException, TweetException;

    /**
     * Retrieves all tweets of a specific user.
     *
     * @param user The user whose tweets are to be retrieved.
     * @return List of tweets by the user.
     */
    List<Tweet> getUserTweets(User user);

    /**
     * Retrieves all tweets liked by a specific user.
     *
     * @param user The user whose liked tweets are to be retrieved.
     * @return List of tweets liked by the user.
     */
    List<Tweet> findByLikesContainsUser(User user);
}
