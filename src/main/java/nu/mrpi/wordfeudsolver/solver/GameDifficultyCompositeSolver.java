package nu.mrpi.wordfeudsolver.solver;

import static nu.mrpi.wordfeudsolver.solver.filter.FilterBuilder.solver;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import nu.mrpi.wordfeudapi.domain.Board;
import nu.mrpi.wordfeudapi.domain.Game;
import nu.mrpi.wordfeudapi.domain.TileMove;
import nu.mrpi.wordfeudsolver.domain.Difficulty;
import nu.mrpi.wordfeudsolver.persistance.GameNotFoundException;
import nu.mrpi.wordfeudsolver.service.GameService;
import nu.mrpi.wordfeudsolver.solver.calculator.WFCalculator;

/**
 * @author Pierre Ingmansson
 */
public class GameDifficultyCompositeSolver implements Solver {
    private final GameService gameService;

    private Map<Difficulty, Solver> solverMap = new EnumMap<>(Difficulty.class);

    public GameDifficultyCompositeSolver(GameService gameService, WFCalculator calculator) {
        this.gameService = gameService;

        solverMap.put(Difficulty.EASY, solver(calculator).pointLimit(20).randomize());
        solverMap.put(Difficulty.MEDIUM, solver(calculator).pointLimit(30).topPercent(50).randomize());
        solverMap.put(Difficulty.HARD, solver(calculator).pointLimit(50).topPercent(30).randomize());
        solverMap.put(Difficulty.NIGHTMARE, solver(calculator));
        solverMap.put(Difficulty.SHORTEST, solver(calculator).length(true));
        solverMap.put(Difficulty.LONGEST, solver(calculator).length(false));
    }

    @Override
    public List<TileMove> solve(Game game, Board board) {
        try {
            Difficulty difficulty = gameService.getGameInfo(game).getDifficulty();

            return solverMap.get(difficulty).solve(game, board);
        } catch (GameNotFoundException e) {
            return solverMap.get(Difficulty.DEFAULT).solve(game, board);
        }
    }
}
