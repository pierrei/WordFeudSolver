package nu.mrpi.wordfeudsolver.persistance.redis;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import nu.mrpi.wordfeudsolver.domain.Difficulty;
import nu.mrpi.wordfeudsolver.domain.GameInfo;

/**
 * @author Pierre Ingmansson
 */
public class GameDAORedisTest {

    private GameDAORedis gameDAORedis;

    @Before
    public void setUp() throws Exception {
        gameDAORedis = new GameDAORedis();
    }

    @Test
    @Ignore
    public void testSaveAndRead() throws Exception {
        GameInfo originalGameInfo = createGameInfo();

        gameDAORedis.updateGameInfo(originalGameInfo);

        GameInfo storedGameInfo = gameDAORedis.getGameInfo(originalGameInfo.getGameId());
        assertEquals(originalGameInfo, storedGameInfo);
    }

    private GameInfo createGameInfo() {
        GameInfo gameInfo = new GameInfo();
        gameInfo.setBotScore(120);
        gameInfo.setOpponentScore(300);
        gameInfo.setOpponent("MrPi");
        gameInfo.setDifficulty(Difficulty.DEFAULT);
        gameInfo.setGameId(12345);
        return gameInfo;
    }
}
