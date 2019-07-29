package nu.mrpi.wordfeudsolver.domain;

import com.google.gson.Gson;

import java.util.Objects;
import nu.mrpi.wordfeudapi.domain.EndGame;
import nu.mrpi.wordfeudapi.domain.Game;

/**
 * @author Pierre Ingmansson
 */
public class GameInfo {
    private Difficulty difficulty;
    private String opponent;
    private int botScore;
    private int opponentScore;
    private long gameId;
    private boolean surrender = false;
    private EndGame endGame;

    public GameInfo() {
    }

    public GameInfo(Game game) {
        update(game);
    }

    public void update(Game game) {
        opponent = game.getOpponentName();
        botScore = game.getMe().getScore();
        opponentScore = game.getOpponent().getScore();
        gameId = game.getId();
        endGame = game.getEndGame();
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isDifficultySet() {
        return this.difficulty != null;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public int getBotScore() {
        return botScore;
    }

    public void setBotScore(int botScore) {
        this.botScore = botScore;
    }

    public int getOpponentScore() {
        return opponentScore;
    }

    public void setOpponentScore(int opponentScore) {
        this.opponentScore = opponentScore;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public boolean isSurrender() {
        return surrender;
    }

    public void setSurrender(boolean surrender) {
        this.surrender = surrender;
    }

    public EndGame getEndGame() {
        return endGame;
    }

    public void setEndGame(final EndGame endGame) {
        this.endGame = endGame;
    }

    public boolean isGameOver() {
        return endGame != null && endGame != EndGame.NotOver;
    }

    public static GameInfo fromJson(final String json) {
        return new Gson().fromJson(json, GameInfo.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GameInfo gameInfo = (GameInfo) o;
        return botScore == gameInfo.botScore &&
               opponentScore == gameInfo.opponentScore &&
               gameId == gameInfo.gameId &&
               surrender == gameInfo.surrender &&
               difficulty == gameInfo.difficulty &&
               Objects.equals(opponent, gameInfo.opponent) &&
               endGame == gameInfo.endGame;
    }

    @Override
    public int hashCode() {
        return Objects
            .hash(difficulty, opponent, botScore, opponentScore, gameId, surrender, endGame);
    }

    @Override
    public String toString() {
        return "GameInfo{" +
               "difficulty=" + difficulty +
               ", opponent='" + opponent + '\'' +
               ", botScore=" + botScore +
               ", opponentScore=" + opponentScore +
               ", gameId=" + gameId +
               ", surrender=" + surrender +
               ", endGame=" + endGame +
               '}';
    }
}
