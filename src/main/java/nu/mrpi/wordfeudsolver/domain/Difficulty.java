package nu.mrpi.wordfeudsolver.domain;

/**
 * @author Pierre Ingmansson
 */
public enum Difficulty {
    EASY, MEDIUM, HARD, NIGHTMARE, SHORTEST, LONGEST;

    public static final Difficulty DEFAULT = MEDIUM;
}
