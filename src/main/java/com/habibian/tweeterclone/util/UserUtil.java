package com.habibian.tweeterclone.util;

import com.habibian.tweeterclone.model.User;

/**
 * Utility class for user-related operations.
 * <p>
 * The UserUtil class provides static methods for checking if a given user is the authenticated user
 * and if a user is followed by the authenticated user. These methods are used in the UserController
 * for efficient handling of user-related operations.
 */
public class UserUtil {

    /**
     * Checks if the provided user is the authenticated user.
     *
     * @param reqUser The authenticated user.
     * @param user2   The user to be checked.
     * @return true if the user is the authenticated user; otherwise, false.
     */
    public static boolean isReqUser(User reqUser, User user2) {
        return reqUser.getId().equals(user2.getId());
    }

    /**
     * Checks if the provided user is followed by the authenticated user.
     *
     * @param reqUser The authenticated user.
     * @param user2   The user to be checked.
     * @return true if the user is followed by the authenticated user; otherwise, false.
     */
    public static boolean isFollowedByReqUser(User reqUser, User user2) {
        return reqUser.getFollowings().contains(user2);
    }
}
