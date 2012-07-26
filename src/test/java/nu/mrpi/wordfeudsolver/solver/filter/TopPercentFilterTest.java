package nu.mrpi.wordfeudsolver.solver.filter;

import java.util.List;

import org.junit.Test;

import nu.mrpi.wordfeudapi.domain.TileMove;

/**
 *
 */
public class TopPercentFilterTest extends AbstractFilterTest<TopPercentFilter> {
    @Override
    protected TopPercentFilter createFilter() {
        return new TopPercentFilter(50);
    }

    @Test
    public void testTenItemsWithFiftyPercent() throws Exception {
        List<TileMove> tileMoves = filter(
                createTileMove(10),
                createTileMove(20),
                createTileMove(30),
                createTileMove(40),
                createTileMove(50),
                createTileMove(60),
                createTileMove(70),
                createTileMove(80),
                createTileMove(90),
                createTileMove(100)
                );

        verifyTileMoves(tileMoves, 10, 20, 30, 40, 50);
    }

    @Test
    public void testFiftyPercentWithOnlyOneItemReturnsTheItem() throws Exception {
        List<TileMove> tileMoves = filter(createTileMove(10));

        verifyTileMoves(tileMoves, 10);
    }

    @Test
    public void testEmptyListReturnsEmptyList() throws Exception {
        List<TileMove> tileMoves = filter();

        verifyTileMoves(tileMoves);
    }

    @Test
    public void testFiftyPercentOfFiveItemsReturnsThree() throws Exception {
        List<TileMove> tileMoves = filter(
                createTileMove(10),
                createTileMove(20),
                createTileMove(30),
                createTileMove(40),
                createTileMove(50));

        verifyTileMoves(tileMoves, 10, 20, 30);
    }

    @Test
    public void testHundredPercentReturnsAllOfThreeItems() throws Exception {
        filter = new TopPercentFilter(100);

        List<TileMove> tileMoves = filter(
                createTileMove(10),
                createTileMove(20),
                createTileMove(30)
                );

        verifyTileMoves(tileMoves, 10, 20, 30);
    }
}
