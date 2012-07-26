package nu.mrpi.wordfeudsolver.solver.filter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nu.mrpi.wordfeudapi.domain.TileMove;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.se)
 */
public class HighestScoreFilter implements TileMoveFilter {
    public static final HighestScoreFilter INSTANCE = new HighestScoreFilter();

    private HighestScoreFilter() {
    }

    public List<TileMove> filter(List<TileMove> tileMoves) {

        sortHighestScoreFirst(tileMoves);

        return tileMoves;
    }

    protected void sortHighestScoreFirst(List<TileMove> tileMoves) {
        Collections.sort(tileMoves, new Comparator<TileMove>() {
            @Override
            public int compare(TileMove move1, TileMove move2) {
                return move2.getPoints() - move1.getPoints();
            }
        });
    }
}
