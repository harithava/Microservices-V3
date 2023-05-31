package com.maersk.multiplication.challenge;

import java.util.List;

public interface ChallengeService {

    /**
     * Verifies if an attempt coming from the presentation layer is correct or not.
     *
     * @param resultAttempt
     * @return the resulting Challenge Attempt object.
     */
    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO resultAttempt);

    /**
     * Get the statistics for a given user.
     *
     * @param alias the user's alias
     * @return a list of last 10 {@link ChallengeAttempt} objects created by user.
     */
    List<ChallengeAttempt> getStatsForUser(final String alias);
}
