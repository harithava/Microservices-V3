package com.maersk.multiplication.challenge;


import com.maersk.multiplication.user.User;
import com.maersk.multiplication.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final UserRepository userRepository;
    private final ChallengeAttemptRepository challengeAttemptRepository;

    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO resultAttempt) {

        // Check if the user already exists for that alias, otherwise create it
        User user = userRepository.findByAlias(resultAttempt.userAlias()).orElseGet(() -> {
            log.info("Creating new user with alias {}", resultAttempt.userAlias());
            return userRepository.save(new User(resultAttempt.userAlias()));
        });

        // Check if the attempt is correct
        boolean isCorrect = resultAttempt.guess() == resultAttempt.factorA() * resultAttempt.factorB();

        // Builds the domain object, Null id for now.
        ChallengeAttempt challengeAttempt = new ChallengeAttempt(null,
                user,
                resultAttempt.factorA(),
                resultAttempt.factorB(),
                resultAttempt.guess(),
                isCorrect);

        // Stores the attempt
        ChallengeAttempt storedAttempt = challengeAttemptRepository.save(challengeAttempt);

        return challengeAttempt;
    }

    @Override
    public List<ChallengeAttempt> getStatsForUser(String alias) {
        return challengeAttemptRepository.findTop10ByUserAliasOrderByIdDesc(alias);
    }
}
