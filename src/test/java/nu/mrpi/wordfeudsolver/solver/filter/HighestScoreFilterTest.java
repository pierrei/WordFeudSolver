package nu.mrpi.wordfeudsolver.solver.filter;

import java.util.List;

import org.junit.Test;

import nu.mrpi.wordfeudapi.domain.TileMove;

/**
 *
 */
public class HighestScoreFilterTest extends AbstractFilterTest<HighestScoreFilter> {
    @Override
    protected HighestScoreFilter createFilter() {
        return HighestScoreFilter.INSTANCE;
    }

    @Test
    public void testSolveForTileMovesWhenOrderedRandomly() throws Exception {
        List<TileMove> result = filter(
                createTileMove(43),
                createTileMove(73),
                createTileMove(21),
                createTileMove(5),
                createTileMove(72),
                createTileMove(3),
                createTileMove(15)
                );

        verifyTileMoves(result, 73, 72, 43, 21, 15, 5, 3);
    }
}
