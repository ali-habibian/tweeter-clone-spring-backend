package com.habibian.tweeterclone.service;

import com.habibian.tweeterclone.exception.TweetException;
import com.habibian.tweeterclone.exception.UserException;
import com.habibian.tweeterclone.model.Like;
import com.habibian.tweeterclone.model.Tweet;
import com.habibian.tweeterclone.model.User;
import com.habibian.tweeterclone.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link LikeService} interface providing methods for managing likes on tweets.
 */
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final TweetService tweetService;

    /**
     * Likes a tweet.
     *
     * @param tweetId The ID of the tweet to like.
     * @param user    The user liking the tweet.
     * @return The like object representing the like on the tweet.
     * @throws UserException  If the user is not valid.
     * @throws TweetException If the tweet is not found.
     */
    @Override
    public Like likeTweet(Long tweetId, User user) throws UserException, TweetException {
        Like isLikeExist = likeRepository.isLikeExist(user.getId(), tweetId);

        if (isLikeExist != null) {
            likeRepository.deleteById(isLikeExist.getId());
            return isLikeExist;
        }

        Tweet tweet = tweetService.finById(tweetId);
        Like like = new Like();
        like.setTweet(tweet);
        like.setUser(user);

        Like savedLike = likeRepository.save(like);

        tweet.getLikes().add(savedLike);
        tweetService.saveExistTweet(tweet);

        return savedLike;
    }

    /**
     * Retrieves all likes for a specific tweet.
     *
     * @param tweetId The ID of the tweet for which to retrieve likes.
     * @return List of likes on the specified tweet.
     * @throws TweetException If the tweet is not found.
     */
    @Override
    public List<Like> getAllLikes(Long tweetId) throws TweetException {
        Tweet tweet = tweetService.finById(tweetId);
        return likeRepository.findAllByTweetId(tweetId);
    }
}
