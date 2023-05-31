package com.maersk.gamification.game.badgeprocessors;

import com.maersk.gamification.challenge.domain.ChallengeSolvedDTO;
import com.maersk.gamification.game.domain.BadgeType;
import com.maersk.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
class LuckyNumberBadgeProcessor implements BadgeProcessor{

    private static final int LUCKY_FACTOR = 42;

    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore,
                                                       List<ScoreCard> scoreCardList,
                                                       ChallengeSolvedDTO solvedDTO) {
        return solvedDTO.factorA() == LUCKY_FACTOR ||
                solvedDTO.factorB() == LUCKY_FACTOR ?
                Optional.of(BadgeType.LUCKY_NUMBER) :
                Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.LUCKY_NUMBER;
    }
}
