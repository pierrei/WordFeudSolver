package nu.mrpi.wordfeudsolver.persistance.redis;

import java.util.List;
import java.util.stream.Collectors;
import nu.mrpi.wordfeudsolver.domain.GameInfo;
import nu.mrpi.wordfeudsolver.domain.PlayerStats;
import nu.mrpi.wordfeudsolver.persistance.GameDAO;
import nu.mrpi.wordfeudsolver.persistance.GameNotFoundException;
import nu.mrpi.wordfeudsolver.service.SettingsService;
import redis.clients.jedis.Jedis;

/**
 * @author Pierre Ingmansson
 */
public class GameDAORedis implements GameDAO {

  private static final String GAME_INFO_PREFIX = "gameinfo.";
  private static final String PLAYER_STATS_PREFIX = "playerstats.";

  private Jedis jedis;

  GameDAORedis(final String host, final int port) {
    jedis = new Jedis(host, port);
  }

  public GameDAORedis(SettingsService settingsService) {
    jedis = new Jedis(settingsService.getRedisHost(), settingsService.getRedisPort());
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

  @Override
  public List<GameInfo> getAllGameInfos() {
    return jedis.keys(GAME_INFO_PREFIX + "*")
        .stream()
        .map(key -> GameInfo.fromJson(jedis.get(key)))
        .collect(Collectors.toList());
  }

  @Override
  public void updatePlayerStats(final PlayerStats playerStats) {
    jedis.set(PLAYER_STATS_PREFIX + playerStats.player(), playerStats.toJson());
  }
}
