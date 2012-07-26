package nu.mrpi.wordfeudsolver.solver;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;

import nu.mrpi.wordfeudapi.domain.Board;
import nu.mrpi.wordfeudapi.domain.Game;
import nu.mrpi.wordfeudapi.domain.TileMove;

/**
 *
 */
public abstract class AbstractSolverTest<T> {
    private Solver mockSolver;
    protected T solver;

    @Before
    public void setUp() throws Exception {
        mockSolver = mock(Solver.class);
        solver = createSolver(mockSolver);
    }

    protected abstract T createSolver(Solver mockSolver);

    protected void returnSolutions(TileMove... tileMoves) {
        when(mockSolver.solve(any(Game.class), any(Board.class))).thenReturn(Arrays.asList(tileMoves));
    }

    protected TileMove createTileMove(int points) {
        TileMove tileMove = mock(TileMove.class);
        when(tileMove.getPoints()).thenReturn(points);
        return tileMove;
    }

    protected Board createBoard() {
        return mock(Board.class);
    }

    protected Game createGame() {
        return mock(Game.class);
    }
}
