package nu.mrpi.wordfeudsolver.chat;

import nu.mrpi.wordfeudapi.WordFeudClient;
import nu.mrpi.wordfeudapi.domain.Game;
import nu.mrpi.wordfeudsolver.service.GameService;

/**
 * @author Pierre Ingmansson
 */
public class CommandData {
    private final WordFeudClient client;
    private final GameService gameService;
    private final MessageStore messageStore;
    private final int gameId;
    private final String fromUsername;
    private final String message;
    private Game game = null;

    public CommandData(final WordFeudClient client, GameService gameService, MessageStore messageStore, final int gameId, final String fromUsername, final String message) {
        this.client = client;
        this.gameService = gameService;
        this.messageStore = messageStore;
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

    public MessageStore getMessageStore() {
        return messageStore;
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

    public Game getGame() {
        if (game == null) {
            game = client.getGame(gameId);
        }
        return game;
    }
}
