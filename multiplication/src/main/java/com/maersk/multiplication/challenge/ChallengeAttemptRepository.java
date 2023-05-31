package com.maersk.multiplication.challenge;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChallengeAttemptRepository extends CrudRepository<ChallengeAttempt, Long> {

    /**
     * @param userAlias
     * @return the last 10 attempts for given user, identified by their alias.
     */
    List<ChallengeAttempt> findTop10ByUserAliasOrderByIdDesc(String userAlias);
}
