package com.maersk.gamification.game.badgeprocessors;

import com.maersk.gamification.challenge.domain.ChallengeSolvedDTO;
import com.maersk.gamification.game.domain.BadgeType;
import com.maersk.gamification.game.domain.ScoreCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LuckyNumberBadgeProcessorTest {

    private LuckyNumberBadgeProcessor badgeProcessor;

    @BeforeEach
    public void setUp() {
        badgeProcessor = new LuckyNumberBadgeProcessor();
    }

    @Test
    public void shouldGiveBadgeIfScoreOverThreshold() {
        Optional<BadgeType> badgeType = badgeProcessor.processForOptionalBadge(10,
                List.of(new ScoreCard(1L, 1L)), new ChallengeSolvedDTO(1L, true,
                        42,10, 1L, "Hariharan"));
        assertThat(badgeType).contains(BadgeType.LUCKY_NUMBER);
    }

    @Test
    public void shouldNotGiveBadgeIfScoreOverThreshold() {
        Optional<BadgeType> badgeType = badgeProcessor.processForOptionalBadge(10,
                List.of(new ScoreCard(1L, 1L)), new ChallengeSolvedDTO(1L, true,
                        43,10, 1L, "Hariharan"));
        assertThat(badgeType).isEmpty();
    }



}