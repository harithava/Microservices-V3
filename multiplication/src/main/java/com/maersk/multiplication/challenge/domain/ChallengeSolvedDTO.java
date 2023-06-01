package com.maersk.multiplication.challenge.domain;


public record ChallengeSolvedDTO(long attemptId, boolean correct, int factorA, int factorB, long userId,
                                 String userAlias) {

}
