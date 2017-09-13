package nu.mrpi.wordfeudsolver.chat.commands;

import nu.mrpi.wordfeudapi.WordFeudClient;
import nu.mrpi.wordfeudapi.domain.Game;
import nu.mrpi.wordfeudsolver.chat.Command;
import nu.mrpi.wordfeudsolver.chat.CommandData;
import nu.mrpi.wordfeudsolver.domain.Difficulty;
import nu.mrpi.wordfeudsolver.domain.GameInfo;
import nu.mrpi.wordfeudsolver.persistance.GameNotFoundException;

public class DifficultyCommand implements Command {
    @Override
    public void executeCommand(CommandData data) {
        WordFeudClient client = data.getClient();

        Difficulty difficulty = parseDifficulty(data);

        if (difficulty != null) {
            setDifficulty(data, difficulty);
        } else {
            try {
                GameInfo gameInfo = data.getGameService().getGameInfo(data.getGameId());

                client.chat(data.getGameId(), "Difficulty level for this game is set to " + gameInfo.getDifficulty().toString().toLowerCase());
            } catch (GameNotFoundException e) {
                // Do nothing
            }
        }
    }

    protected void setDifficulty(CommandData data, Difficulty difficulty) {
        WordFeudClient client = data.getClient();
        Game game = client.getGame(data.getGameId());

        data.getGameService().setGameDifficulty(game, difficulty);

        client.chat(data.getGameId(), "Difficulty was set to " + difficulty.toString().toLowerCase());
    }

    private Difficulty parseDifficulty(CommandData data) {
        Difficulty difficulty = null;

        String receivedData = data.getMessage().toLowerCase();
        if (receivedData.endsWith("easy")) {
            difficulty = nu.mrpi.wordfeudsolver.domain.Difficulty.EASY;
        } else if (receivedData.endsWith("medium")) {
            difficulty = nu.mrpi.wordfeudsolver.domain.Difficulty.MEDIUM;
        } else if (receivedData.endsWith("hard")) {
            difficulty = nu.mrpi.wordfeudsolver.domain.Difficulty.HARD;
        } else if (receivedData.endsWith("nightmare")) {
            difficulty = nu.mrpi.wordfeudsolver.domain.Difficulty.NIGHTMARE;
        }
        return difficulty;
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
