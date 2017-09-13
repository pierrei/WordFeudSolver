package nu.mrpi.wordfeudsolver.chat;

import nu.mrpi.wordfeudapi.WordFeudClient;
import nu.mrpi.wordfeudsolver.service.GameService;

/**
 * @author Pierre Ingmansson
 */
public class CommandData {
    private final WordFeudClient client;
    private final GameService gameService;
    private final int gameId;
    private final String fromUsername;
    private final String message;

    public CommandData(final WordFeudClient client, GameService gameService, final int gameId, final String fromUsername, final String message) {
        this.client = client;
        this.gameService = gameService;
        this.gameId = gameId;
        this.fromUsername = fromUsername;
        this.message = message;
    }

    public WordFeudClient getClient() {
        return client;
    }

    public GameService getGameService() {
        return gameService;
    }

    public int getGameId() {
        return gameId;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public String getMessage() {
        return message;
    }
}