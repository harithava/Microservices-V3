package com.maersk.gamification.challenge.domain;


public record ChallengeSolvedDTO(long attemptId, boolean correct, int factorA, int factorB, long userId,
                                 String userAlias) {

}
