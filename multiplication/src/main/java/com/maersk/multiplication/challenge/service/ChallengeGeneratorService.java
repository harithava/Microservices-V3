package com.maersk.multiplication.challenge.service;

import com.maersk.multiplication.challenge.domain.Challenge;

public interface ChallengeGeneratorService {

    /**
     * @return a randomly generated challenge with factors between 11 and 99
     */
    Challenge randomChallenge();
}
