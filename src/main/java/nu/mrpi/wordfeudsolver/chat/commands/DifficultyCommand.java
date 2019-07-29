package nu.mrpi.wordfeudsolver.chat.commands;

import nu.mrpi.wordfeudapi.WordFeudClient;
import nu.mrpi.wordfeudapi.domain.Game;
import nu.mrpi.wordfeudsolver.chat.Command;
import nu.mrpi.wordfeudsolver.chat.CommandData;
import nu.mrpi.wordfeudsolver.chat.MessageStore;
import nu.mrpi.wordfeudsolver.domain.Difficulty;
import nu.mrpi.wordfeudsolver.domain.GameInfo;
import nu.mrpi.wordfeudsolver.persistance.GameNotFoundException;
import nu.mrpi.wordfeudsolver.service.GameService;

public class DifficultyCommand implements Command {
    @Override
    public void executeCommand(CommandData data) {
        WordFeudClient client = data.getClient();

        Difficulty difficulty = parseDifficulty(data);
        MessageStore messageStore = data.getMessageStore();
        Game game = data.getGame();

        if (difficulty != null) {
            setDifficulty(data, difficulty);
        } else {
            try {
                GameInfo gameInfo = data.getGameService().getGameInfo(data.getGameId());

                client.chat(data.getGameId(), messageStore.getDifficultyLevelMessage(game.getLanguageLocale(), gameInfo.getDifficulty()));
            } catch (GameNotFoundException e) {
                // Do nothing
            }
        }
    }

    void setDifficulty(CommandData data, Difficulty difficulty) {
        WordFeudClient client = data.getClient();
        Game game = data.getGame();
        MessageStore messageStore = data.getMessageStore();
        GameService gameService = data.getGameService();

        boolean wasDifficultySet = gameService.isGameDifficultySet(game);

        gameService.setGameDifficulty(game, difficulty);

        if (wasDifficultySet)
            client.chat(data.getGameId(), messageStore.getDifficultySetMessage(game.getLanguageLocale(), difficulty));
        else
            client.chat(data.getGameId(), messageStore.getInitialDifficultySetMessage(game.getLanguageLocale(), difficulty));
    }

    private Difficulty parseDifficulty(CommandData data) {
        MessageStore messageStore = data.getMessageStore();

        String receivedData = data.getMessage().toLowerCase();
        for (Difficulty difficulty : Difficulty.values()) {
            if (receivedData.endsWith(messageStore.getLocalizedDifficulty(data.getGame().getLanguageLocale(), difficulty))) {
                return difficulty;
            }
        }

        return null;
    }

    public static Command forLevel(final Difficulty difficulty) {
        return new DifficultyCommand() {
            @Override
            public void executeCommand(CommandData data) {
                this.setDifficulty(data, difficulty);
            }
        };
    }
}
