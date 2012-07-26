package nu.mrpi.wordfeudsolver.chat;

import org.apache.log4j.Logger;

import nu.mrpi.wordfeudapi.WordFeudClient;
import nu.mrpi.wordfeudsolver.log.GameLogger;
import nu.mrpi.wordfeudsolver.service.GameService;

/**
 * @author Pierre Ingmansson
 */
public class ChatWorker {
    private final GameLogger log = new GameLogger(Logger.getLogger(getClass()));

    private final WordFeudClient wordFeudClient;
    private final GameService gameService;
    private static final String ADMIN_USER = "MrPi";

    public ChatWorker(final WordFeudClient wordFeudClient, GameService gameService) {
        this.wordFeudClient = wordFeudClient;
        this.gameService = gameService;
    }

    public void processChatMessage(final int gameId, final String fromUsername, final String message) {
        boolean chatMessageProcessed = false;

        for (final ChatCommand chatCommand : ChatCommand.values()) {
            if (messageMatchesCommand(message, chatCommand) && verifyAccess(fromUsername, chatCommand)) {
                log.info(gameId, "Responding to chat message \"" + message + "\" using command \"" + chatCommand + "\"");

                chatCommand.executeCommand(new CommandData(wordFeudClient, gameService, gameId, fromUsername, message));
                chatMessageProcessed = true;
            }
        }

        if (!chatMessageProcessed) {
            log.info(gameId, "Received chat message from " + fromUsername + ": \"" + message + "\"");
        }
    }

    private boolean verifyAccess(final String fromUsername, final ChatCommand chatCommand) {
        return !chatCommand.isAdminCommand() || ADMIN_USER.equals(fromUsername);
    }

    private boolean messageMatchesCommand(final String message, final ChatCommand chatCommand) {
        return message.toLowerCase().startsWith(chatCommand.toString().toLowerCase());
    }
}
