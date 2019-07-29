package nu.mrpi.wordfeudsolver.solver.filter;

import java.util.List;

import nu.mrpi.wordfeudapi.domain.TileMove;

/**
 *
 */
public class TopPercentFilter implements TileMoveFilter {
    private final int topPercent;

    public TopPercentFilter(int topPercent) {
        this.topPercent = topPercent;
    }

    @Override
    public List<TileMove> filter(List<TileMove> tileMoves) {
        double multiplier = ((double) topPercent) / 100.0;
        return tileMoves.subList(0, (int) Math.ceil(((double) tileMoves.size()) * multiplier));
    }
}
