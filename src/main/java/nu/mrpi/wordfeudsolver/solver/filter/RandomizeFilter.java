package nu.mrpi.wordfeudsolver.solver.filter;

import java.util.Collections;
import java.util.List;

import nu.mrpi.wordfeudapi.domain.TileMove;

/**
 *
 */
public class RandomizeFilter implements TileMoveFilter {
    public static final RandomizeFilter INSTANCE = new RandomizeFilter();

    private RandomizeFilter() {
    }

    @Override
    public List<TileMove> filter(List<TileMove> tileMoves) {
        Collections.shuffle(tileMoves);

        return tileMoves;
    }
}
