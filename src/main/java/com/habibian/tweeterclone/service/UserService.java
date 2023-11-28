package com.habibian.tweeterclone.service;

import com.habibian.tweeterclone.exception.UserException;
import com.habibian.tweeterclone.model.User;

import java.util.List;

/**
 * Service interface for managing user-related operations.
 */
public interface UserService {

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user with the specified ID.
     * @throws UserException If the user is not found.
     */
    User findUserById(Long userId) throws UserException;

    /**
     * Retrieves a user's profile based on their JWT token.
     *
     * @param jwt The JWT token associated with the user.
     * @return The user profile based on the JWT token.
     * @throws UserException If the user is not found.
     */
    User findUserProfileByJwt(String jwt) throws UserException;

    /**
     * Updates a user's information.
     *
     * @param userId The ID of the user to update.
     * @param req   The updated user information.
     * @return The updated user.
     * @throws UserException If the user is not found.
     */
    User updateUser(Long userId, User req) throws UserException;

    /**
     * Follows another user.
     *
     * @param userId The ID of the user initiating the follow action.
     * @param user   The user to be followed.
     * @return The user who is now being followed.
     * @throws UserException If the users are not valid.
     */
    User followUser(Long userId, User user) throws UserException;

    /**
     * Searches for users based on a query.
     *
     * @param query The search query.
     * @return List of users matching the search query.
     */
    List<User> searchUser(String query);
}
