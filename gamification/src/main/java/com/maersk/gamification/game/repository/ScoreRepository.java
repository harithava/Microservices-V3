package com.maersk.gamification.game.repository;

import com.maersk.gamification.game.domain.LeaderBoardRow;
import com.maersk.gamification.game.domain.ScoreCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Handles CRUD operation with ScoreCards and other related score queries
 */
public interface ScoreRepository extends CrudRepository<ScoreCard, Long> {

    /**
     * Gets the total score for a given user: the sum of the scores of all their ScoreCards
     * @param userId
     * @return
     */
    @Query("SELECT SUM(s.score) FROM ScoreCard s WHERE s.userId = :userId GROUP BY s.userId")
    Optional<Integer> getTotalScoreForUser(@Param("userId") Long userId);

    @Query("SELECT NEW com.maersk.gamification.game.domain.LeaderBoardRow(s.userId, SUM(s.score)) " +
            "FROM ScoreCard s " +
            "GROUP BY s.userId ORDER BY SUM(s.score) DESC")
    List<LeaderBoardRow> findFirst10();

    /**
     * Retrieves all the ScoreCards for a given user, identified by his user id.
     *
     * @param userId the id of the user
     * @return a list containing all the ScoreCards for the given user,
     * sorted by most recent.
     */
    List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);

}
