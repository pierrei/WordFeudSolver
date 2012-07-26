package nu.mrpi.wordfeudsolver.solver.calculator;

import nu.mrpi.wordfeudapi.domain.Board;
import nu.mrpi.wordfeudapi.domain.Game;
import nu.mrpi.wordfeudapi.domain.TileMove;
import nu.mrpi.wordfeudapi.domain.RuleSet;

import java.util.Set;

/**
 * @author Pierre Ingmansson
 */
public interface WFCalculator {
    public Set<TileMove> findSolutions(Game game, Board board);

    boolean isRulesetSupported(RuleSet ruleSet);
}
