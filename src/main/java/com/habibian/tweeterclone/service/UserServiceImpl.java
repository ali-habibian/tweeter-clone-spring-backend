package com.habibian.tweeterclone.service;

import com.habibian.tweeterclone.config.JwtProvider;
import com.habibian.tweeterclone.exception.UserException;
import com.habibian.tweeterclone.model.User;
import com.habibian.tweeterclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link UserService} interface that provides
 * methods for managing user-related operations.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user with the specified ID.
     * @throws UserException If the user is not found.
     */
    @Override
    public User findUserById(Long userId) throws UserException {
        return userRepository.findById(userId).orElseThrow(() ->
                new UserException("user not found with id " + userId)
        );
    }

    /**
     * Retrieves a user's profile based on their JWT token.
     *
     * @param jwt The JWT token associated with the user.
     * @return The user profile based on the JWT token.
     * @throws UserException If the user is not found.
     */
    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserException("user not found with email " + email);
        }

        return user;
    }

    /**
     * Updates a user's information.
     *
     * @param userId The ID of the user to update.
     * @param req    The updated user information.
     * @return The updated user.
     * @throws UserException If the user is not found.
     */
    @Override
    public User updateUser(Long userId, User req) throws UserException {
        User user = findUserById(userId);

        if (req.getFullname() != null) {
            user.setFullname(req.getFullname());
        }
        if (req.getImage() != null) {
            user.setImage(req.getImage());
        }
        if (req.getBackgroundImage() != null) {
            user.setBackgroundImage(req.getBackgroundImage());
        }
        if (req.getBirthDate() != null) {
            user.setBirthDate(req.getBirthDate());
        }
        if (req.getLocation() != null) {
            user.setLocation(req.getLocation());
        }
        if (req.getBio() != null) {
            user.setBio(req.getBio());
        }
        if (req.getWebsite() != null) {
            user.setWebsite(req.getWebsite());
        }

        return userRepository.save(user);
    }

    /**
     * Follows another user.
     *
     * @param userId The ID of the user initiating the follow action.
     * @param user   The user to be followed.
     * @return The user who is now being followed.
     * @throws UserException If the users are not valid.
     */
    @Override
    public User followUser(Long userId, User user) throws UserException {
        User followToUser = findUserById(userId);

        if (user.getFollowings().contains(followToUser) && followToUser.getFollowers().contains(user)) { // Unfollow
            user.getFollowings().remove(followToUser);
            followToUser.getFollowers().remove(user);
        } else { // Follow
            user.getFollowings().add(followToUser);
            followToUser.getFollowers().add(user);
        }

        userRepository.save(followToUser);
        userRepository.save(user);

        return followToUser;
    }

    /**
     * Searches for users based on a query.
     *
     * @param query The search query.
     * @return List of users matching the search query.
     */
    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }
}
