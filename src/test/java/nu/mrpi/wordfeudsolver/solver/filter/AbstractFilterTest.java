package nu.mrpi.wordfeudsolver.solver.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;

import nu.mrpi.wordfeudapi.domain.TileMove;

/**
 *
 */
public abstract class AbstractFilterTest<F extends TileMoveFilter> {
    protected F filter;

    @Before
    public void setUp() throws Exception {
        filter = createFilter();
    }

    protected abstract F createFilter();

    protected List<TileMove> filter(TileMove... tileMoves) {
        return filter.filter(Arrays.asList(tileMoves));
    }

    protected TileMove createTileMove(int points) {
        TileMove tileMove = mock(TileMove.class);
        when(tileMove.getPoints()).thenReturn(points);
        return tileMove;
    }

    protected void verifyTileMoves(List<TileMove> tileMoves, int... tileMovePoints) {
        assertNotNull(tileMoves);
        assertEquals(tileMovePoints.length, tileMoves.size());
        for (int i = 0, tileMovesSize = tileMoves.size(); i < tileMovesSize; i++) {
            assertEquals(tileMovePoints[i], tileMoves.get(i).getPoints());
        }
    }

}
