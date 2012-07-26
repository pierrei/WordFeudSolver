package nu.mrpi.wordfeudsolver.solver.filter;

import java.util.List;

import org.junit.Test;

import nu.mrpi.wordfeudapi.domain.TileMove;

/**
 *
 */
public class PointLimitFilterTest extends AbstractFilterTest<PointLimitFilter> {

    @Override
    protected PointLimitFilter createFilter() {
        return new PointLimitFilter(30);
    }

    @Test
    public void testSolveForABunchOfTileMovesWithPointsBothOverAndUnderTheLimit() throws Exception {
        List<TileMove> result = filter(
                createTileMove(21),
                createTileMove(70),
                createTileMove(50),
                createTileMove(40),
                createTileMove(31),
                createTileMove(30),
                createTileMove(29),
                createTileMove(25)
                );

        verifyTileMoves(result, 21, 30, 29, 25);
    }

}
