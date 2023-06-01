package com.maersk.multiplication.challenge.service;


import com.maersk.multiplication.challenge.domain.ChallengeAttempt;
import com.maersk.multiplication.challenge.domain.ChallengeAttemptDTO;
import com.maersk.multiplication.challenge.repository.ChallengeAttemptRepository;
import com.maersk.multiplication.challenge.serviceclients.GamificationServiceClient;
import com.maersk.multiplication.user.domain.User;
import com.maersk.multiplication.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final UserRepository userRepository;
    private final ChallengeAttemptRepository challengeAttemptRepository;
    private final GamificationServiceClient gamificationServiceClient;

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

        // Sends the attempt to gamification and prints the response
        boolean status = gamificationServiceClient.sendAttempt(storedAttempt);
        log.info("Gamification service response: {}", status);

        return storedAttempt;
    }

    @Override
    public List<ChallengeAttempt> getStatsForUser(String alias) {
        return challengeAttemptRepository.findTop10ByUserAliasOrderByIdDesc(alias);
    }
}
