package com.habibian.tweeterclone.util;

import com.habibian.tweeterclone.model.Like;
import com.habibian.tweeterclone.model.Tweet;
import com.habibian.tweeterclone.model.User;

/**
 * Utility class for common operations related to tweets.
 */
public class TweetUtil {

    /**
     * Checks if a tweet is liked by the requesting user.
     *
     * @param reqUser The user making the request.
     * @param tweet   The tweet to check for likes.
     * @return True if the tweet is liked by the requesting user, false otherwise.
     */
    public static boolean isLikedByReqUser(User reqUser, Tweet tweet) {
        for (Like like : tweet.getLikes()) {
            if (like.getUser().getId().equals(reqUser.getId())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if a tweet is retweeted by the requesting user.
     *
     * @param reqUser The user making the request.
     * @param tweet   The tweet to check for retweets.
     * @return True if the tweet is retweeted by the requesting user, false otherwise.
     */
    public static boolean isRetweetedByReqUser(User reqUser, Tweet tweet) {
        for (User user : tweet.getRetweetUser()) {
            if (user.getId().equals(reqUser.getId())) {
                return true;
            }
        }

        return false;
    }
}
