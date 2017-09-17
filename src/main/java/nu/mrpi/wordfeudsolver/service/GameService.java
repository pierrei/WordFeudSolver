package nu.mrpi.wordfeudsolver.service;

import nu.mrpi.wordfeudapi.domain.Game;
import nu.mrpi.wordfeudsolver.domain.Difficulty;
import nu.mrpi.wordfeudsolver.domain.GameInfo;
import nu.mrpi.wordfeudsolver.persistance.GameDAO;
import nu.mrpi.wordfeudsolver.persistance.GameNotFoundException;

/**
 * @author Pierre Ingmansson
 */
public class GameService {
    private GameDAO gameDAO;

    public GameService(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    public void setGameDifficulty(final Game game, final Difficulty difficulty) {
        GameInfo gameInfo;
        try {
            gameInfo = gameDAO.getGameInfo(game.getId());

            gameInfo.setDifficulty(difficulty);
        } catch (GameNotFoundException e) {
            gameInfo = new GameInfo(game);
            gameInfo.setDifficulty(difficulty);
        }

        gameDAO.updateGameInfo(gameInfo);
    }

    public void surrender(final Game game) {
        GameInfo gameInfo;
        try {
            gameInfo = gameDAO.getGameInfo(game.getId());

            gameInfo.setSurrender(true);
        } catch (GameNotFoundException e) {
            gameInfo = new GameInfo(game);
            gameInfo.setSurrender(true);
        }

        gameDAO.updateGameInfo(gameInfo);
    }

    public void storeGameInfo(Game game) {
        GameInfo gameInfo;
        try {
            gameInfo = gameDAO.getGameInfo(game.getId());

            gameInfo.update(game);
        } catch (GameNotFoundException e) {
            gameInfo = new GameInfo(game);
        }

        gameDAO.updateGameInfo(gameInfo);
    }

    public GameInfo getGameInfo(long gameId) throws GameNotFoundException {
        return gameDAO.getGameInfo(gameId);
    }

    public GameInfo getGameInfo(Game game) throws GameNotFoundException {
        return getGameInfo(game.getId());
    }

    public boolean isGameDifficultySet(Game game) {
        try {
            return getGameInfo(game).getDifficulty() != null;
        } catch (GameNotFoundException e) {
            return false;
        }
    }
}
