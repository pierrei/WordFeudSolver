package nu.mrpi.wordfeudsolver.worker;

import java.util.Arrays;

import nu.mrpi.wordfeudapi.WordFeudClient;
import nu.mrpi.wordfeudapi.domain.Board;
import nu.mrpi.wordfeudapi.domain.Game;
import nu.mrpi.wordfeudapi.domain.PlaceResult;
import nu.mrpi.wordfeudapi.domain.Rack;
import nu.mrpi.wordfeudapi.domain.SwapResult;
import nu.mrpi.wordfeudapi.domain.Tile;
import nu.mrpi.wordfeudapi.domain.TileMove;
import nu.mrpi.wordfeudapi.exception.WordFeudException;
import nu.mrpi.wordfeudsolver.chat.MessageStore;
import nu.mrpi.wordfeudsolver.domain.Difficulty;
import nu.mrpi.wordfeudsolver.persistance.GameNotFoundException;
import nu.mrpi.wordfeudsolver.service.GameService;
import nu.mrpi.wordfeudsolver.service.SettingsService;
import nu.mrpi.wordfeudsolver.solver.Solver;

/**
 *
 */
public class MoveMakerWorker extends AbstractWorker implements Worker{
    private static final byte TRY_LIMIT = 10;
    private static final int POINT_BRAG_LIMIT = 70;

    private final WordFeudClient wordFeudClient;
    private final Solver solver;
    private final MessageStore messageStore;
    private final GameService gameService;
    private final SettingsService settingsService;

    public MoveMakerWorker(WordFeudClient wordFeudClient, Solver solver, MessageStore messageStore, GameService gameService, SettingsService settingsService) {
        this.wordFeudClient = wordFeudClient;
        this.solver = solver;
        this.messageStore = messageStore;
        this.gameService = gameService;
        this.settingsService = settingsService;
    }

    @Override
    public void doWork() {
        boolean noGamesWithMyTurn = true;

        for (final Game game : wordFeudClient.getGames()) {
            if (game.isMyTurn() && gameService.isGameDifficultySet(game)) {
                final Game gameWithTiles = wordFeudClient.getGame(game.getId());
                final Board board = wordFeudClient.getBoard(gameWithTiles);

                findAndPlaceSolution(gameWithTiles, board);

                noGamesWithMyTurn = false;
            }

            gameService.storeGameInfo(game);
        }

        if (noGamesWithMyTurn) {
            log.debug("No games found where it's my turn");
        }
    }

    private void findAndPlaceSolution(final Game gameWithTiles, final Board board) {
        logWorkingOnGame(gameWithTiles);

        boolean solved = false;
        byte tries = 0;
        for (final TileMove tileMove : solver.solve(gameWithTiles, board)) {
            if (tries != TRY_LIMIT) {
                logSolution(gameWithTiles, tileMove);

                try {
                    final int points = placeSolution(gameWithTiles, tileMove);

                    actOnPoints(gameWithTiles, points);
                    solved = true;
                    break;
                } catch (WordFeudException e) {
                    log.warn(gameWithTiles, "Could not place solution (" + e.getMessage() + "). Trying the next one...");
                    tries++;
                }
            } else {
                log.warn(gameWithTiles, "Reached try limit of " + TRY_LIMIT + "; passing game!");
                break;
            }
        }

        if (!solved) {
            giveUpTurn(gameWithTiles);
        }
    }

    private void logWorkingOnGame(final Game game) {
        final String logMessage = "Getting to work on game between " + game.getMe().getUsername() + " and " +
                game.getOpponentName() + " (score " + game.getMe().getScore() + "/" +
                game.getOpponent().getScore() +", rack: " + game.getMyRack() + ")";

        log.info(game, logMessage);
    }

    private int placeSolution(final Game game, final TileMove tileMove) {
        final PlaceResult result = wordFeudClient.makeMove(game, tileMove);
        log.info(game, "Placed \"" + tileMove.getWord() + "\" okay for " + result.getPoints() + " points");

        if (result.getPoints() != tileMove.getPoints()) {
            log.warn(game, "Points calculated did not match points received from server! We thought " + tileMove.getPoints() + ", but got " + result.getPoints());
        }

        return result.getPoints();
    }

    private void actOnPoints(final Game game, final int points) {
        if (points >= POINT_BRAG_LIMIT && settingsService.shouldSendBragMessages()) {
            final String message = messageStore.getRandomBragMessage(game.getLanguageLocale());

            log.info(game, "Sending brag message \"" + message + "\" to " + game.getOpponent().getUsername());

            wordFeudClient.chat(game, message);
        }
    }

    private void giveUpTurn(final Game game) {
        final Rack myRack = game.getMyRack();
        if (myRack.hasDuplicateLetters()) {
            try {
                swapTiles(game, myRack.getDuplicateLetters());
            } catch (WordFeudException e) {
                passGame(game);
            }
        } else {
            passGame(game);
        }
    }

    private void swapTiles(final Game game, final char[] duplicateLetters) {
        log.info(game, "Could not place any solution, swapping tiles " + Arrays.toString(duplicateLetters));
        final SwapResult result = wordFeudClient.swap(game, duplicateLetters);
        log.info(game, "Got new tiles; " + Arrays.toString(result.getNewTiles()));
    }

    private void passGame(final Game game) {
        log.info(game, "Could not place any solution, passing turn..");
        wordFeudClient.pass(game.getId());
    }

    private void logSolution(final Game game, final TileMove tileMove) {
        final Tile firstTile = tileMove.getTiles()[0];
        log.info(game, "Found solution \"" + tileMove.getWord() + "\" for " + tileMove.getPoints() + " points. Attempting to place at [" + firstTile.getX() + "," + firstTile.getY() + "] " + (tileMove.isHorizontalWord() ? "horizontally" : "vertically") + "...");
    }

}
