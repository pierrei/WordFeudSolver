package nu.mrpi.wordfeudsolver.persistance.redis;

import nu.mrpi.wordfeudsolver.domain.GameInfo;
import nu.mrpi.wordfeudsolver.persistance.GameDAO;
import nu.mrpi.wordfeudsolver.persistance.GameNotFoundException;
import redis.clients.jedis.Jedis;

/**
 * @author Pierre Ingmansson
 */
public class GameDAORedis implements GameDAO {
    private static final String GAME_INFO_PREFIX = "gameinfo.";

    private Jedis jedis;

    public GameDAORedis() {
        jedis = new Jedis("localhost");
    }

    @Override
    public void updateGameInfo(GameInfo game) {
        jedis.set(GAME_INFO_PREFIX + game.getGameId(), game.toJson());
    }

    @Override
    public GameInfo getGameInfo(long gameId) throws GameNotFoundException {
        String json = jedis.get(GAME_INFO_PREFIX + gameId);
        if (json == null) {
            throw new GameNotFoundException();
        }
        return GameInfo.fromJson(json);
    }


}
