package nu.mrpi.wordfeudsolver.solver.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nu.mrpi.wordfeudapi.domain.TileMove;

/**
 *
 */
public class PointLimitFilter implements TileMoveFilter {
    private final int pointMaxLimit;

    public PointLimitFilter(int pointMaxLimit) {
        this.pointMaxLimit = pointMaxLimit;
    }

    @Override
    public List<TileMove> filter(List<TileMove> tileMoves) {
        tileMoves = new ArrayList<>(tileMoves);
        removeSolutionsExceedingPointLimit(tileMoves);

        return tileMoves;
    }

    private void removeSolutionsExceedingPointLimit(List<TileMove> solutions) {
        Iterator<TileMove> tileMoveIterator = solutions.iterator();
        while (tileMoveIterator.hasNext()) {
            TileMove tileMove = tileMoveIterator.next();
            if (tileMove.getPoints() > pointMaxLimit) {
                tileMoveIterator.remove();
            }
        }
    }
}
