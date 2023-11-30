package com.habibian.tweeterclone.controller;

import com.habibian.tweeterclone.dto.UserDTO;
import com.habibian.tweeterclone.dto.mapper.UserDtoMapper;
import com.habibian.tweeterclone.exception.UserException;
import com.habibian.tweeterclone.model.User;
import com.habibian.tweeterclone.service.UserService;
import com.habibian.tweeterclone.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class handling user-related operations through API endpoints.
 * <p>
 * The UserController manages user profile retrieval, user search, user updates, and user following.
 * It provides endpoints for retrieving user profiles, searching for users, updating user information,
 * and following/unfollowing users.
 * <p>
 * This controller collaborates with the UserService for efficient user-related functionality
 * and data handling. Additionally, it utilizes UserDtoMapper and UserUtil for DTO conversion
 * and user-related utility operations.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Endpoint for retrieving the user profile of the authenticated user.
     *
     * @param jwt The user's JWT token for authentication.
     * @return ResponseEntity containing the UserDTO representing the user profile.
     * @throws UserException If user-related exceptions occur.
     */
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        UserDTO userDto = UserDtoMapper.toUserDto(user);
        userDto.setReq_user(true);

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

    /**
     * Endpoint for retrieving the user profile by user ID.
     *
     * @param jwt     The user's JWT token for authentication.
     * @param userId  The ID of the user profile to be retrieved.
     * @return ResponseEntity containing the UserDTO representing the user profile.
     * @throws UserException If user-related exceptions occur.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@RequestHeader("Authorization") String jwt,
                                               @PathVariable Long userId) throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);

        User user = userService.findUserById(userId);
        UserDTO userDto = UserDtoMapper.toUserDto(user);
        userDto.setReq_user(UserUtil.isReqUser(reqUser, user));
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

    /**
     * Endpoint for searching users based on a query string.
     *
     * @param jwt   The user's JWT token for authentication.
     * @param query The query string for user search.
     * @return ResponseEntity containing a list of UserDTOs representing the search results.
     * @throws UserException If user-related exceptions occur.
     */
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUser(@RequestHeader("Authorization") String jwt,
                                                    @RequestParam String query) throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);

        List<User> users = userService.searchUser(query);
        List<UserDTO> userDTOs = UserDtoMapper.toUserDTOs(users);

        return new ResponseEntity<>(userDTOs, HttpStatus.ACCEPTED);
    }

    /**
     * Endpoint for updating user information.
     *
     * @param jwt The user's JWT token for authentication.
     * @param req The updated user information.
     * @return ResponseEntity containing the UserDTO representing the updated user profile.
     * @throws UserException If user-related exceptions occur.
     */
    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestHeader("Authorization") String jwt,
                                              @RequestBody User req) throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);

        User updatedUser = userService.updateUser(reqUser.getId(), req);
        UserDTO userDto = UserDtoMapper.toUserDto(updatedUser);

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

    /**
     * Endpoint for following/unfollowing a user.
     *
     * @param jwt    The user's JWT token for authentication.
     * @param userId The ID of the user to be followed/unfollowed.
     * @return ResponseEntity containing the UserDTO representing the updated user profile.
     * @throws UserException If user-related exceptions occur.
     */
    @PutMapping("/{userId}/follow")
    public ResponseEntity<UserDTO> followUser(@RequestHeader("Authorization") String jwt,
                                              @PathVariable Long userId) throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);

        User user = userService.followUser(userId, reqUser);
        UserDTO userDto = UserDtoMapper.toUserDto(user);
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }
}
