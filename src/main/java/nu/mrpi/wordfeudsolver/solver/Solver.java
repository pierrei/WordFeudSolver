package nu.mrpi.wordfeudsolver.solver;

import java.util.List;

import nu.mrpi.wordfeudapi.domain.Board;
import nu.mrpi.wordfeudapi.domain.Game;
import nu.mrpi.wordfeudapi.domain.TileMove;

/**
 * @author Pierre Ingmansson
 */
public interface Solver {
    public List<TileMove> solve(Game game, Board board);
}
