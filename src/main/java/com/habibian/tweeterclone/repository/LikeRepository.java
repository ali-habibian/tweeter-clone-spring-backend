package com.habibian.tweeterclone.repository;

import com.habibian.tweeterclone.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on Like entities.
 */
public interface LikeRepository extends JpaRepository<Like, Long> {

    /**
     * Checks if a like exists for a specific user and tweet.
     *
     * @param userId  The ID of the user.
     * @param tweetId The ID of the tweet.
     * @return The Like entity if the like exists; otherwise, null.
     */
    @Query("SELECT l FROM Like l WHERE l.user.id = :userId AND l.tweet.id = :tweetId")
    Like isLikeExist(@Param("userId") Long userId, @Param("tweetId") Long tweetId);

    /**
     * Retrieves all likes for a specific tweet.
     *
     * @param tweetId The ID of the tweet.
     * @return A list of Like entities associated with the tweet.
     */
    List<Like> findAllByTweetId(Long tweetId);
}
