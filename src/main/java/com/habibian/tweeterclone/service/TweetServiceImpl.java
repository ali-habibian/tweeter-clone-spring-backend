package com.habibian.tweeterclone.service;

import com.habibian.tweeterclone.exception.TweetException;
import com.habibian.tweeterclone.exception.UserException;
import com.habibian.tweeterclone.model.Tweet;
import com.habibian.tweeterclone.model.User;
import com.habibian.tweeterclone.repository.TweetRepository;
import com.habibian.tweeterclone.request.TweetReplyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of the {@link TweetService} interface that provides
 * methods for managing Tweet-related operations.
 */
@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;

    /**
     * Creates a new tweet.
     *
     * @param req  The tweet request containing the tweet content.
     * @param user The user creating the tweet.
     * @return The created tweet.
     * @throws UserException If the user is not valid.
     */
    @Override
    public Tweet createTweet(Tweet req, User user) throws UserException {
        Tweet tweet = new Tweet();
        tweet.setContent(req.getContent());
        tweet.setCreatedAt(LocalDateTime.now());
        tweet.setImage(req.getImage());
        tweet.setUser(user);
        tweet.setIsReply(false);
        tweet.setIsTweet(true);
        tweet.setVideo(req.getVideo());

        return tweetRepository.save(tweet);
    }

    /**
     * Retrieves all tweets.
     *
     * @return List of all tweets.
     */
    @Override
    public List<Tweet> findAllTweets() {
        return tweetRepository.findAllByIsTweetTrueOrderByCreatedAtDesc();
    }

    /**
     * Retweets a tweet.
     *
     * @param tweetId The ID of the tweet to retweet.
     * @param user    The user retweeting the tweet.
     * @return The retweeted tweet.
     * @throws UserException  If the user is not valid.
     * @throws TweetException If the tweet is not found.
     */
    @Override
    public Tweet retweet(Long tweetId, User user) throws UserException, TweetException {
        Tweet tweet = finById(tweetId);

        if (tweet.getRetweetUser().contains(user)) {
            tweet.getRetweetUser().remove(user);
        } else {
            tweet.getRetweetUser().add(user);
        }

        return tweetRepository.save(tweet);
    }

    /**
     * Retrieves a tweet by its ID.
     *
     * @param tweetId The ID of the tweet to retrieve.
     * @return The tweet with the specified ID.
     * @throws TweetException If the tweet is not found.
     */
    @Override
    public Tweet finById(Long tweetId) throws TweetException {
        return tweetRepository.findById(tweetId).orElseThrow(() ->
                new TweetException("Tweet not found with id " + tweetId)
        );
    }

    /**
     * Deletes a tweet by its ID.
     *
     * @param tweetId The ID of the tweet to delete.
     * @param userId  The ID of the user deleting the tweet.
     * @throws UserException  If the user is not valid.
     * @throws TweetException If the tweet is not found.
     */
    @Override
    public void deleteByTweetId(Long tweetId, Long userId) throws UserException, TweetException {
        Tweet tweet = finById(tweetId);

        if (!userId.equals(tweet.getUser().getId())) {
            throw new UserException("You can't delete another user's tweet");
        }

        tweetRepository.deleteById(tweetId);
    }

    /**
     * Removes a user from a tweet (unlike or unretweet).
     *
     * @param tweetId The ID of the tweet to remove the user from.
     * @param user    The user to be removed from the tweet.
     * @return The modified tweet.
     * @throws UserException  If the user is not valid.
     * @throws TweetException If the tweet is not found.
     */
    @Override
    public Tweet removeFromTweet(Long tweetId, User user) throws UserException, TweetException {
        return null;
    }

    /**
     * Creates a reply to a tweet.
     *
     * @param req  The tweet reply request containing the reply content.
     * @param user The user creating the reply.
     * @return The created reply tweet.
     * @throws UserException  If the user is not valid.
     * @throws TweetException If the original tweet is not found.
     */
    @Override
    public Tweet createReply(TweetReplyRequest req, User user) throws UserException, TweetException {
        Tweet replyFor = finById(req.getTweetId());

        Tweet tweet = new Tweet();
        tweet.setContent(req.getContent());
        tweet.setCreatedAt(LocalDateTime.now());
        tweet.setImage(req.getImage());
        tweet.setUser(user);
        tweet.setIsReply(true);
        tweet.setIsTweet(false);
        tweet.setReplyFor(replyFor);

        Tweet savedReply = tweetRepository.save(tweet);
        replyFor.getReplyTweets().add(savedReply);

        return tweetRepository.save(replyFor);
    }

    /**
     * Retrieves all tweets of a specific user.
     *
     * @param user The user whose tweets are to be retrieved.
     * @return List of tweets by the user.
     */
    @Override
    public List<Tweet> getUserTweets(User user) {
        return tweetRepository
                .findAllByRetweetUserContainsOrUser_IdAndIsTweetTrueOrderByCreatedAtDesc(user, user.getId());
    }

    /**
     * Retrieves all tweets liked by a specific user.
     *
     * @param user The user whose liked tweets are to be retrieved.
     * @return List of tweets liked by the user.
     */
    @Override
    public List<Tweet> findByLikesContainsUser(User user) {
        return tweetRepository.findAllByLikesUser_Id(user.getId());
    }
}
