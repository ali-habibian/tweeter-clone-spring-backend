package com.habibian.tweeterclone.controller;

import com.habibian.tweeterclone.dto.TweetDTO;
import com.habibian.tweeterclone.dto.mapper.TweetDtoMapper;
import com.habibian.tweeterclone.exception.TweetException;
import com.habibian.tweeterclone.exception.UserException;
import com.habibian.tweeterclone.model.Tweet;
import com.habibian.tweeterclone.model.User;
import com.habibian.tweeterclone.request.TweetReplyRequest;
import com.habibian.tweeterclone.response.ApiResponse;
import com.habibian.tweeterclone.service.TweetService;
import com.habibian.tweeterclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling tweet-related API endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tweets")
public class TweetController {

    private final TweetService tweetService;
    private final UserService userService;

    /**
     * Creates a new tweet.
     *
     * @param req The request body containing tweet details.
     * @param jwt The authorization token.
     * @return ResponseEntity containing the created TweetDTO.
     * @throws UserException  If user-related exceptions occur.
     * @throws TweetException If tweet-related exceptions occur.
     */
    @PostMapping("/create")
    public ResponseEntity<TweetDTO> createTweet(@RequestBody Tweet req,
                                                @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException {

        User user = userService.findUserProfileByJwt(jwt);

        Tweet tweet = tweetService.createTweet(req, user);
        TweetDTO tweetDTO = TweetDtoMapper.toTweetDto(tweet, user);

        return new ResponseEntity<>(tweetDTO, HttpStatus.CREATED);
    }

    /**
     * Replies to a tweet.
     *
     * @param req The request body containing tweet reply details.
     * @param jwt The authorization token.
     * @return ResponseEntity containing the created TweetDTO for the reply.
     * @throws UserException  If user-related exceptions occur.
     * @throws TweetException If tweet-related exceptions occur.
     */
    @PostMapping("/reply")
    public ResponseEntity<TweetDTO> replyTweet(@RequestBody TweetReplyRequest req,
                                               @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException {

        User user = userService.findUserProfileByJwt(jwt);

        Tweet tweet = tweetService.createReply(req, user);
        TweetDTO tweetDTO = TweetDtoMapper.toTweetDto(tweet, user);

        return new ResponseEntity<>(tweetDTO, HttpStatus.CREATED);
    }

    /**
     * Retweets a tweet.
     *
     * @param tweetId The ID of the tweet to retweet.
     * @param jwt     The authorization token.
     * @return ResponseEntity containing the retweeted TweetDTO.
     * @throws UserException  If user-related exceptions occur.
     * @throws TweetException If tweet-related exceptions occur.
     */
    @PutMapping("/{tweetId}/retweet")
    public ResponseEntity<TweetDTO> retweet(@PathVariable Long tweetId,
                                            @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException {

        User user = userService.findUserProfileByJwt(jwt);

        Tweet tweet = tweetService.retweet(tweetId, user);
        TweetDTO tweetDTO = TweetDtoMapper.toTweetDto(tweet, user);

        return new ResponseEntity<>(tweetDTO, HttpStatus.OK);
    }

    /**
     * Retrieves a tweet by its ID.
     *
     * @param tweetId The ID of the tweet to retrieve.
     * @param jwt     The authorization token.
     * @return ResponseEntity containing the retrieved TweetDTO.
     * @throws UserException  If user-related exceptions occur.
     * @throws TweetException If tweet-related exceptions occur.
     */
    @GetMapping("/{tweetId}")
    public ResponseEntity<TweetDTO> findTweetById(@PathVariable Long tweetId,
                                                  @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException {

        User user = userService.findUserProfileByJwt(jwt);

        Tweet tweet = tweetService.finById(tweetId);
        TweetDTO tweetDTO = TweetDtoMapper.toTweetDto(tweet, user);

        return new ResponseEntity<>(tweetDTO, HttpStatus.OK);
    }

    /**
     * Deletes a tweet by its ID.
     *
     * @param tweetId The ID of the tweet to delete.
     * @param jwt     The authorization token.
     * @return ResponseEntity containing the API response for tweet deletion.
     * @throws UserException  If user-related exceptions occur.
     * @throws TweetException If tweet-related exceptions occur.
     */
    @DeleteMapping("/{tweetId}")
    public ResponseEntity<ApiResponse> deleteTweetById(@PathVariable Long tweetId,
                                                       @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException {

        User user = userService.findUserProfileByJwt(jwt);

        tweetService.deleteByTweetId(tweetId, user.getId());

        ApiResponse res = new ApiResponse("Tweet deleted successfully", true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * Retrieves all tweets.
     *
     * @param jwt The authorization token.
     * @return ResponseEntity containing a list of all TweetDTOs.
     * @throws UserException  If user-related exceptions occur.
     * @throws TweetException If tweet-related exceptions occur.
     */
    @GetMapping
    public ResponseEntity<List<TweetDTO>> getAllTweets(@RequestHeader("Authorization") String jwt)
            throws UserException, TweetException {

        User user = userService.findUserProfileByJwt(jwt);

        List<Tweet> allTweets = tweetService.findAllTweets();
        List<TweetDTO> tweetDTOS = TweetDtoMapper.toTweetDTOS(allTweets, user);

        return new ResponseEntity<>(tweetDTOS, HttpStatus.OK);
    }

    /**
     * Retrieves all tweets for a specific user.
     *
     * @param userId The ID of the user for whom to retrieve tweets.
     * @param jwt    The authorization token.
     * @return ResponseEntity containing a list of TweetDTOs for the user.
     * @throws UserException  If user-related exceptions occur.
     * @throws TweetException If tweet-related exceptions occur.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TweetDTO>> getUserAllTweets(@PathVariable Long userId,
                                                           @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException {

        User user = userService.findUserProfileByJwt(jwt);

        List<Tweet> allTweets = tweetService.getUserTweets(user);
        List<TweetDTO> tweetDTOS = TweetDtoMapper.toTweetDTOS(allTweets, user);

        return new ResponseEntity<>(tweetDTOS, HttpStatus.OK);
    }

    /**
     * Retrieves all tweets liked by a specific user.
     *
     * @param userId The ID of the user for whom to retrieve liked tweets.
     * @param jwt    The authorization token.
     * @return ResponseEntity containing a list of TweetDTOs liked by the user.
     * @throws UserException  If user-related exceptions occur.
     * @throws TweetException If tweet-related exceptions occur.
     */
    @GetMapping("/user/{userId}/likes")
    public ResponseEntity<List<TweetDTO>> findTweetByLikesContainsUser(@PathVariable Long userId,
                                                                       @RequestHeader("Authorization") String jwt)
            throws UserException, TweetException {

        User user = userService.findUserProfileByJwt(jwt);

        List<Tweet> allTweets = tweetService.findByLikesContainsUser(user);
        List<TweetDTO> tweetDTOS = TweetDtoMapper.toTweetDTOS(allTweets, user);

        return new ResponseEntity<>(tweetDTOS, HttpStatus.OK);
    }
}
