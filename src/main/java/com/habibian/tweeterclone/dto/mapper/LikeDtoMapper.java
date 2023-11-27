package com.habibian.tweeterclone.dto.mapper;

import com.habibian.tweeterclone.dto.LikeDTO;
import com.habibian.tweeterclone.dto.TweetDTO;
import com.habibian.tweeterclone.dto.UserDTO;
import com.habibian.tweeterclone.model.Like;
import com.habibian.tweeterclone.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for converting Like entities to LikeDTOs.
 */
public class LikeDtoMapper {

    /**
     * Converts a Like entity to a LikeDTO.
     *
     * @param like    The Like entity to be converted.
     * @param reqUser The requesting user.
     * @return The corresponding LikeDTO.
     */
    public static LikeDTO toLikeDto(Like like, User reqUser) {
        UserDTO userDto = UserDtoMapper.toUserDto(like.getUser());
        TweetDTO tweetDto = TweetDtoMapper.toTweetDto(like.getTweet(), reqUser);

        return LikeDTO.builder()
                .id(like.getId())
                .tweet(tweetDto)
                .user(userDto)
                .build();
    }

    /**
     * Converts a list of Like entities to a list of LikeDTOs.
     *
     * @param likes   The list of Like entities to be converted.
     * @param reqUser The requesting user.
     * @return The corresponding list of LikeDTOs.
     */
    public static List<LikeDTO> toLikeDTOS(List<Like> likes, User reqUser) {
        List<LikeDTO> likeDTOS = new ArrayList<>();

        for (Like like : likes) {
            likeDTOS.add(toLikeDto(like, reqUser));
        }

        return likeDTOS;
    }
}
