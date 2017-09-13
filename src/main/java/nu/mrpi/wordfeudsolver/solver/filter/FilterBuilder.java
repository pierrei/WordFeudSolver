package nu.mrpi.wordfeudsolver.solver.filter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nu.mrpi.wordfeudapi.domain.Board;
import nu.mrpi.wordfeudapi.domain.Game;
import nu.mrpi.wordfeudapi.domain.TileMove;
import nu.mrpi.wordfeudsolver.solver.Solver;
import nu.mrpi.wordfeudsolver.solver.calculator.WFCalculator;

/**
 *
 */
public class FilterBuilder {

    public static OngoingSolverBuilder solver(final WFCalculator calculator) {
        return new OngoingSolverBuilder(new Solver() {
            @Override
            public List<TileMove> solve(Game game, Board board) {
                return new ArrayList<>(calculator.findSolutions(game, board));
            }
        });
    }

    public static OngoingSolverBuilder solver(final Solver rootSolver) {
        return new OngoingSolverBuilder(rootSolver);
    }

    public static class OngoingSolverBuilder implements Solver {
        private List<TileMoveFilter> filters = new LinkedList<>();
        private Solver solver;

        OngoingSolverBuilder(final Solver solver) {
            this.solver = solver;
        }

        public OngoingSolverBuilder randomize() {
            filters.add(RandomizeFilter.INSTANCE);
            return this;
        }

        public OngoingSolverBuilder topPercent(int topPercent) {
            filters.add(new TopPercentFilter(topPercent));
            return this;
        }

        public OngoingSolverBuilder pointLimit(int pointLimit) {
            filters.add(new PointLimitFilter(pointLimit));
            return this;
        }

        @Override
        public List<TileMove> solve(Game game, Board board) {
            List<TileMove> tileMoves = solver.solve(game, board);

            for (TileMoveFilter filter : filters) {
                tileMoves = filter.filter(tileMoves);
            }

            return tileMoves;
        }
    }
}
