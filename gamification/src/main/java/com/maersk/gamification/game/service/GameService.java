package com.maersk.gamification.game.service;

import com.maersk.gamification.game.domain.BadgeType;
import com.maersk.gamification.challenge.domain.ChallengeSolvedDTO;

import java.util.List;

public interface GameService {

    /**
     * Process a new attempt from a given user.
     *
     * @param challenge the challenge data with user details, factors, etc.
     * @return a {@link  GameResult} object containing the new score and badge cards obtained
     */
    GameResult newAttemptForUser(ChallengeSolvedDTO challenge);

    public record GameResult(int score, List<BadgeType> badges) {}

}
