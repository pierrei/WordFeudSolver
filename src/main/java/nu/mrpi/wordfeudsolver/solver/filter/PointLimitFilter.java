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
        tileMoves.removeIf(tileMove -> tileMove.getPoints() > pointMaxLimit);

        return tileMoves;
    }
}
