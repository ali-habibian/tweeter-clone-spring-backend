package com.habibian.tweeterclone.dto.mapper;

import com.habibian.tweeterclone.dto.TweetDTO;
import com.habibian.tweeterclone.dto.UserDTO;
import com.habibian.tweeterclone.model.Tweet;
import com.habibian.tweeterclone.model.User;
import com.habibian.tweeterclone.util.TweetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for converting Tweet entities to TweetDTOs.
 */
public class TweetDtoMapper {

    /**
     * Converts a Tweet entity to a TweetDTO.
     *
     * @param tweet   The Tweet entity to be converted.
     * @param reqUser The requesting user.
     * @return The corresponding TweetDTO.
     */
    // TODO Refactor: remove duplicated codes
    public static TweetDTO toTweetDto(Tweet tweet, User reqUser) {
        UserDTO user = UserDtoMapper.toUserDto(reqUser);

        boolean isLiked = TweetUtil.isLikedByReqUser(reqUser, tweet);
        boolean isRetweet = TweetUtil.isRetweetedByReqUser(reqUser, tweet);

        List<Long> retweetUsersId = new ArrayList<>();
        for (User retweetUser : tweet.getRetweetUser()) {
            retweetUsersId.add(retweetUser.getId());
        }

        return TweetDTO.builder()
                .id(tweet.getId())
                .content(tweet.getContent())
                .createdAt(tweet.getCreatedAt())
                .image(tweet.getImage())
                .totalLikes(tweet.getLikes().size())
                .totalReplies(tweet.getReplyTweets().size())
                .totalRetweets(tweet.getRetweetUser().size())
                .user(user)
                .isLiked(isLiked)
                .isRetweet(isRetweet)
                .retweetUserIds(retweetUsersId)
                .replyTweets(toTweetDTOS(tweet.getReplyTweets(), reqUser))
                .video(tweet.getVideo())
                .build();
    }

    /**
     * Converts a list of Tweet entities to a list of TweetDTOs.
     *
     * @param tweets  The list of Tweet entities to be converted.
     * @param reqUser The requesting user.
     * @return The corresponding list of TweetDTOs.
     */
    public static List<TweetDTO> toTweetDTOS(List<Tweet> tweets, User reqUser) {
        List<TweetDTO> tweetDTOS = new ArrayList<>();

        for (Tweet tweet : tweets) {
            tweetDTOS.add(toReplyTweetDto(tweet, reqUser));
        }

        return tweetDTOS;
    }

    /**
     * Converts a Reply Tweet entity to a TweetDTO.
     *
     * @param tweet   The Reply Tweet entity to be converted.
     * @param reqUser The requesting user.
     * @return The corresponding TweetDTO.
     */
    private static TweetDTO toReplyTweetDto(Tweet tweet, User reqUser) {
        UserDTO user = UserDtoMapper.toUserDto(reqUser);

        boolean isLiked = TweetUtil.isLikedByReqUser(reqUser, tweet);
        boolean isRetweet = TweetUtil.isRetweetedByReqUser(reqUser, tweet);

        List<Long> retweetUsersId = new ArrayList<>();
        for (User retweetUser : tweet.getRetweetUser()) {
            retweetUsersId.add(retweetUser.getId());
        }

        return TweetDTO.builder()
                .id(tweet.getId())
                .content(tweet.getContent())
                .createdAt(tweet.getCreatedAt())
                .image(tweet.getImage())
                .totalLikes(tweet.getLikes().size())
                .totalReplies(tweet.getReplyTweets().size())
                .totalRetweets(tweet.getRetweetUser().size())
                .user(user)
                .isLiked(isLiked)
                .isRetweet(isRetweet)
                .retweetUserIds(retweetUsersId)
                .video(tweet.getVideo())
                .build();
    }
}
