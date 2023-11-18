package com.habibian.tweeterclone.repository;

import com.habibian.tweeterclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for managing User entities in the application.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a user by their email.
     *
     * @param email The email of the user to retrieve.
     * @return The user with the specified email or null if not found.
     */
    User findByEmail(String email);

    /**
     * Performs a user search based on the provided query string.
     *
     * @param query The query string to search for in user full names and emails.
     * @return A list of users matching the search criteria.
     */
    @Query("SELECT DISTINCT u FROM User u WHERE u.fullname LIKE %:query% OR u.email LIKE %:query%")
    List<User> searchUser(@Param("query") String query);
}
