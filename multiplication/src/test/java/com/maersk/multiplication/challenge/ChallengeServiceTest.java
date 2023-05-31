package com.maersk.multiplication.challenge;

import com.maersk.multiplication.user.User;
import com.maersk.multiplication.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChallengeServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChallengeAttemptRepository challengeAttemptRepository;

    private ChallengeService challengeService;

    @BeforeEach
    public void setUp() {
        challengeService = new ChallengeServiceImpl(userRepository, challengeAttemptRepository);
    }

    @Test
    public void checkCorrectAttemptTest() {
        // given
        given(challengeAttemptRepository.save(any())).will(returnsFirstArg());
        ChallengeAttemptDTO attemptDTO = new
                ChallengeAttemptDTO(50, 60, "Hariharan", 3000);

        // when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        // then
        then(resultAttempt.isCorrect()).isTrue();
        verify(userRepository).save(new User("Hariharan"));
        verify(challengeAttemptRepository).save(resultAttempt);
    }

    @Test
    public void checkExistingUserTest() {
        // given
        User existingUser = new User(1L, "Hariharan");
        given(userRepository.findByAlias("Hariharan")).willReturn(Optional.of(existingUser));
        given(challengeAttemptRepository.save(any())).will(returnsFirstArg());
        ChallengeAttemptDTO attemptDTO = new
                ChallengeAttemptDTO(50, 60, "Hariharan", 3000);

        // when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        // then
        then(resultAttempt.isCorrect()).isTrue();
        then(resultAttempt.getUser()).isEqualTo(existingUser);
        verify(userRepository, never()).save(new User("Hariharan"));
        verify(challengeAttemptRepository).save(resultAttempt);
    }

    @Test
    public void checkWrongAttemptTest() {
        // given
        given(challengeAttemptRepository.save(any())).will(returnsFirstArg());
        ChallengeAttemptDTO attemptDTO = new
                ChallengeAttemptDTO(50, 60, "Hariharan", 5000);

        // when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        // then
        then(resultAttempt.isCorrect()).isFalse();
    }

    @Test
    public void retrieveStatsTest() {
        // given
        String alias = "Hariharan";
        User user = new User(alias);
        ChallengeAttempt attempt1 = new ChallengeAttempt(1L, user, 50, 60, 3000, true);
        ChallengeAttempt attempt2 = new ChallengeAttempt(1L, user, 50, 70, 3500, true);
        List<ChallengeAttempt> lastAttempts = List.of(attempt1, attempt2);
        given(challengeAttemptRepository.findTop10ByUserAliasOrderByIdDesc(alias)).willReturn(lastAttempts);

        // when
        List<ChallengeAttempt> statsForUser = challengeService.getStatsForUser(alias);

        // then
        then(statsForUser).isEqualTo(lastAttempts);
    }


}