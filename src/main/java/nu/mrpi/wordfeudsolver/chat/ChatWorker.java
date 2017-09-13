package nu.mrpi.wordfeudsolver.chat;

import nu.mrpi.wordfeudapi.domain.Game;
import nu.mrpi.wordfeudsolver.domain.GameInfo;
import org.apache.log4j.Logger;

import nu.mrpi.wordfeudapi.WordFeudClient;
import nu.mrpi.wordfeudsolver.log.GameLogger;
import nu.mrpi.wordfeudsolver.service.GameService;

import java.util.Locale;
import java.util.MissingResourceException;

/**
 * @author Pierre Ingmansson
 */
public class ChatWorker {
    private final GameLogger log = new GameLogger(Logger.getLogger(getClass()));

    private final WordFeudClient wordFeudClient;
    private final GameService gameService;
    private final MessageStore messageStore;
    private static final String ADMIN_USER = "MrPi";

    public ChatWorker(final WordFeudClient wordFeudClient, GameService gameService, MessageStore messageStore) {
        this.wordFeudClient = wordFeudClient;
        this.gameService = gameService;
        this.messageStore = messageStore;
    }

    public void processChatMessage(final int gameId, final String fromUsername, final String message) {
        boolean chatMessageProcessed = false;
        Game game = wordFeudClient.getGame(gameId);

        for (final ChatCommand chatCommand : ChatCommand.values()) {
            if (messageMatchesCommand(game.getLanguageLocale(), message, chatCommand) && verifyAccess(fromUsername, chatCommand)) {
                log.info(gameId, "Responding to chat message \"" + message + "\" using command \"" + chatCommand + "\"");

                chatCommand.executeCommand(new CommandData(wordFeudClient, gameService, messageStore, gameId, fromUsername, message));
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

    private boolean messageMatchesCommand(final Locale locale, final String message, final ChatCommand chatCommand) {
        String commandReceived = message.toLowerCase().replace("\"", "");

        String command = chatCommand.toString().toLowerCase();
        String translatedChatCommand = command;
        try {
            translatedChatCommand = messageStore.getChatCommand(locale, command);
        } catch (MissingResourceException e) {
            // Ignore
        }

        return commandReceived.startsWith(command) || commandReceived.startsWith(translatedChatCommand);
    }
}
