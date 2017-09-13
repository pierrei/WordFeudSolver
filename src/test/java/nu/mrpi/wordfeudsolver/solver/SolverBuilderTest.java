package nu.mrpi.wordfeudsolver.solver;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import nu.mrpi.wordfeudapi.domain.TileMove;
import nu.mrpi.wordfeudsolver.solver.filter.FilterBuilder;

/**
 *
 */
public class SolverBuilderTest extends AbstractSolverTest<FilterBuilder.OngoingSolverBuilder> {
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        returnSolutions(
                createTileMove(10),
                createTileMove(20),
                createTileMove(5),
                createTileMove(30),
                createTileMove(40),
                createTileMove(1),
                createTileMove(3),
                createTileMove(50),
                createTileMove(45),
                createTileMove(25)
        );
    }

    @Test
    public void testTopPercentAndPointLimit() throws Exception {
        this.solver = this.solver.topPercent(50).pointLimit(10);

        List<TileMove> solve = solver.solve(createGame(), createBoard());

        assertEquals(2, solve.size());
        assertEquals(10, solve.get(0).getPoints());
        assertEquals(5, solve.get(1).getPoints());
    }

    @Override
    protected FilterBuilder.OngoingSolverBuilder createSolver(Solver mockSolver) {
        return FilterBuilder.solver(mockSolver);
    }
}
