package nu.mrpi.wordfeudsolver.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Pierre Ingmansson
 */
public class GameInfoTest {
    @Test
    public void testToJson() throws Exception {
        GameInfo gameInfo = createGameInfo();

        String jsonString = gameInfo.toJson();
        assertEquals("{\"difficulty\":\"HARD\",\"opponent\":\"MrPi\",\"botScore\":120,\"opponentScore\":300,\"gameId\":1234}", jsonString);
    }

    private GameInfo createGameInfo() {
        GameInfo gameInfo = new GameInfo();
        gameInfo.setBotScore(120);
        gameInfo.setOpponentScore(300);
        gameInfo.setOpponent("MrPi");
        gameInfo.setDifficulty(Difficulty.HARD);
        gameInfo.setGameId(1234);
        return gameInfo;
    }
}
