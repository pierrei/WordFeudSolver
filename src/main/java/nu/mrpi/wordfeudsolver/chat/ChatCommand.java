package nu.mrpi.wordfeudsolver.chat;

import nu.mrpi.wordfeudapi.WordFeudClient;
import nu.mrpi.wordfeudapi.domain.BoardType;
import nu.mrpi.wordfeudapi.domain.Game;
import nu.mrpi.wordfeudapi.domain.RuleSet;
import nu.mrpi.wordfeudsolver.domain.Difficulty;
import nu.mrpi.wordfeudsolver.domain.GameInfo;
import nu.mrpi.wordfeudsolver.persistance.GameNotFoundException;

/**
 * @author Pierre Ingmansson
 */
public enum ChatCommand {
    Status(true, new Command() {
        public void executeCommand(final CommandData data) {
            final WordFeudClient client = data.getClient();

            client.chat(data.getGameId(), buildStatusString(client));
        }

        private String buildStatusString(final WordFeudClient client) {
            final StringBuilder status = new StringBuilder();

            final Game[] games = client.getGames();

            int activeGames = 0;
            int gamesInLead = 0;
            int finishedGames = 0;
            int gamesLost = 0;
            String lostTo = "";

            for (final Game game : games) {
                if (game.isRunning()) {
                    activeGames++;

                    if (game.isInLead()) {
                        gamesInLead++;
                    }
                } else {
                    finishedGames++;

                    if (game.isLost()) {
                        gamesLost++;

                        if (lostTo.length() > 0) {
                            lostTo += ", " + game.getOpponentName();
                        } else {
                            lostTo += game.getOpponentName();
                        }
                    }
                }
            }

            status.append(activeGames).append(" running games (").append(gamesInLead).append(" in lead), ").append(finishedGames).append(" finished; ");

            if (gamesLost > 0) {
                status.append("won ").append(finishedGames - gamesLost).append(", ");
                status.append("lost ").append(gamesLost).append(" (to ").append(lostTo).append(")");
            } else {
                status.append("won all!");
            }
            status.append(".");

            return status.toString();
        }
    }),

    Invite(true, new Command() {
        public void executeCommand(final CommandData data) {
            final WordFeudClient client = data.getClient();

            final String[] tokens = data.getMessage().split("\\s");
            if (tokens.length > 1) {
                RuleSet ruleSet = RuleSet.Swedish;
                BoardType boardType = BoardType.Normal;

                try {
                    if (tokens.length > 2) {
                        ruleSet = RuleSet.fromString(tokens[2]);
                    }
                    if (tokens.length > 3) {
                        boardType = BoardType.fromString(tokens[3]);
                    }

                    String username = tokens[1];
                    client.invite(username, ruleSet, boardType);
                    client.chat(data.getGameId(), "Invite to " + username + " sent!");
                } catch (Exception e) {
                    sendInstructions(data, client);
                }
            } else {
                sendInstructions(data, client);
            }
        }

        private void sendInstructions(final CommandData data, final WordFeudClient client) {
            client.chat(data.getGameId(), "Invite should look like this; \"invite <username> [ruleset] [boardType]\". Ex: \"invite Dude swedish random\"");
        }
    }),

    Difficulty(false, new Command() {
        @Override
        public void executeCommand(CommandData data) {
            WordFeudClient client = data.getClient();

            Difficulty difficulty = parseDifficulty(data);

            if (difficulty != null) {
                Game game = client.getGame(data.getGameId());

                data.getGameService().setGameDifficulty(game, difficulty);

                client.chat(data.getGameId(), "Difficulty was set to " + difficulty.toString().toLowerCase());
            } else {
                try {
                    GameInfo gameInfo = data.getGameService().getGameInfo(data.getGameId());

                    client.chat(data.getGameId(), "Difficulty level for this game is set to " + gameInfo.getDifficulty().toString().toLowerCase());
                } catch (GameNotFoundException e) {
                    // Do nothing
                }
            }
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
    });

    private final boolean adminCommand;
    private final Command command;

    ChatCommand(final boolean adminCommand, final Command command) {
        this.adminCommand = adminCommand;
        this.command = command;
    }

    public boolean isAdminCommand() {
        return adminCommand;
    }

    public void executeCommand(final CommandData commandData) {
        command.executeCommand(commandData);
    }
}
