package com.habibian.tweeterclone.repository;

import com.habibian.tweeterclone.model.Tweet;
import com.habibian.tweeterclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository interface for managing Tweet entities in the database.
 * Extends JpaRepository, providing basic CRUD operations for Tweet entities.
 */
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    /**
     * Retrieves all tweets that are marked as original tweets (not retweets),
     * ordered by their creation timestamp in descending order.
     *
     * @return List of tweets ordered by creation timestamp.
     */
    List<Tweet> findAllByIsTweetTrueOrderByCreatedAtDesc();

    /**
     * Retrieves all tweets based on the given user's retweets or original tweets,
     * ordered by their creation timestamp in descending order.
     *
     * @param user   The user whose tweets or retweets are to be retrieved.
     * @param userId The ID of the user for whom to retrieve tweets or retweets.
     * @return List of tweets ordered by creation timestamp.
     */
    List<Tweet> findAllByRetweetUserContainsOrUser_IdAndIsTweetTrueOrderByCreatedAtDesc(User user, Long userId);

    /**
     * Retrieves all tweets that have been liked by the given user,
     * ordered by their creation timestamp in descending order.
     *
     * @param user The user whose liked tweets are to be retrieved.
     * @return List of tweets ordered by creation timestamp.
     */
    List<Tweet> findAllByLikesContainingOrderByCreatedAtDesc(User user);

    /**
     * Retrieves all tweets that have been liked by a user with the specified ID,
     * using a custom JPQL query.
     *
     * @param userId The ID of the user whose liked tweets are to be retrieved.
     * @return List of tweets ordered by creation timestamp.
     */
    @Query("select t from Tweet t join t.likes l where l.user.id = :userId")
    List<Tweet> findAllByLikesUser_Id(Long userId);
}
