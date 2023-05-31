package com.maersk.gamification.game.service;

import com.maersk.gamification.game.domain.BadgeCard;
import com.maersk.gamification.game.domain.BadgeType;
import com.maersk.gamification.game.domain.LeaderBoardRow;
import com.maersk.gamification.game.repository.BadgeRepository;
import com.maersk.gamification.game.repository.ScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LeaderBoardServiceImplTest {

    private LeaderBoardServiceImpl leaderBoardService;

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private BadgeRepository badgeRepository;

    @BeforeEach
    public void setUp() {
        leaderBoardService = new LeaderBoardServiceImpl(scoreRepository, badgeRepository);
    }

    @Test
    public void retrieveLeaderBoardTes() {
        // given
        LeaderBoardRow scoreRow = new LeaderBoardRow(1L, 300L, List.of());
        given(scoreRepository.findFirst10()).willReturn(List.of(scoreRow));
        given(badgeRepository.findByUserIdOrderByBadgeTimestampDesc(1L))
                .willReturn(List.of(new BadgeCard(1L, BadgeType.LUCKY_NUMBER)));

        // when
        List<LeaderBoardRow> actual = leaderBoardService.getCurrentLeaderBoard();


        // then
        List<LeaderBoardRow> expected = List.of(new LeaderBoardRow(1L, 300L,
                List.of(BadgeType.LUCKY_NUMBER.getDescription())));
        then(actual).isEqualTo(expected);
    }
}