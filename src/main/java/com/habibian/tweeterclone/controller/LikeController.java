package com.habibian.tweeterclone.controller;

import com.habibian.tweeterclone.dto.LikeDTO;
import com.habibian.tweeterclone.dto.mapper.LikeDtoMapper;
import com.habibian.tweeterclone.exception.TweetException;
import com.habibian.tweeterclone.exception.UserException;
import com.habibian.tweeterclone.model.Like;
import com.habibian.tweeterclone.model.User;
import com.habibian.tweeterclone.service.LikeService;
import com.habibian.tweeterclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class handling like-related operations through API endpoints.
 * <p>
 * The LikeController manages the creation and retrieval of likes associated with tweets.
 * It provides endpoints for liking a tweet and fetching all likes for a specific tweet.
 * <p>
 * This controller collaborates with the UserService and LikeService to handle user
 * authentication and like-related functionality efficiently.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final UserService userService;
    private final LikeService likeService;

    /**
     * Endpoint for liking a tweet.
     *
     * @param tweetId The ID of the tweet to be liked.
     * @param jwt     The user's JWT token for authentication.
     * @return ResponseEntity containing the LikeDTO representing the liked tweet.
     * @throws UserException  If user-related exceptions occur.
     * @throws TweetException If tweet-related exceptions occur.
     */
    @PostMapping("/{tweetId}/likes")
    public ResponseEntity<LikeDTO> likeTweet(@PathVariable Long tweetId,
                                             @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException {

        User user = userService.findUserProfileByJwt(jwt);

        Like like = likeService.likeTweet(tweetId, user);
        LikeDTO likeDto = LikeDtoMapper.toLikeDto(like, user);

        return new ResponseEntity<>(likeDto, HttpStatus.CREATED);
    }

    /**
     * Endpoint for fetching all likes associated with a specific tweet.
     *
     * @param tweetId The ID of the tweet for which likes are to be retrieved.
     * @param jwt     The user's JWT token for authentication.
     * @return ResponseEntity containing a list of LikeDTOs representing the likes for the tweet.
     * @throws UserException  If user-related exceptions occur.
     * @throws TweetException If tweet-related exceptions occur.
     */
    @GetMapping("/tweet/{tweetId}")
    public ResponseEntity<List<LikeDTO>> getAllLikes(@PathVariable Long tweetId,
                                                     @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException {

        User user = userService.findUserProfileByJwt(jwt);

        List<Like> allLikes = likeService.getAllLikes(tweetId);
        List<LikeDTO> likeDTOS = LikeDtoMapper.toLikeDTOS(allLikes, user);

        return new ResponseEntity<>(likeDTOS, HttpStatus.OK);
    }
}
