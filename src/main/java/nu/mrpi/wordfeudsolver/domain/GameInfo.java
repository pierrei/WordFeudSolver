package nu.mrpi.wordfeudsolver.domain;

import com.google.gson.Gson;

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

    public static GameInfo fromJson(final String json) {
        return new Gson().fromJson(json, GameInfo.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameInfo gameInfo = (GameInfo) o;

        return botScore == gameInfo.botScore &&
                gameId == gameInfo.gameId &&
                opponentScore == gameInfo.opponentScore &&
                difficulty == gameInfo.difficulty &&
                surrender == gameInfo.surrender &&
                opponent.equals(gameInfo.opponent);

    }

    @Override
    public int hashCode() {
        int result = difficulty.hashCode();
        result = 31 * result + opponent.hashCode();
        result = 31 * result + botScore;
        result = 31 * result + opponentScore;
        result = 31 * result + (int) (gameId ^ (gameId >>> 32));
        result = 31 * result + (surrender ? 1 : 0);
        return result;
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
               '}';
    }
}
