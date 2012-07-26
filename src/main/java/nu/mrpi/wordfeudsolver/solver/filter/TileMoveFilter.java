package nu.mrpi.wordfeudsolver.solver.filter;

import java.util.List;

import nu.mrpi.wordfeudapi.domain.TileMove;

/**
 *
 */
public interface TileMoveFilter {
    public List<TileMove> filter(List<TileMove> tileMoves);
}
