package nu.mrpi.wordfeudsolver;

import static nu.mrpi.wordfeudsolver.stats.StatsUpdater.addGameToPlayerStats;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nu.mrpi.wordfeudsolver.domain.Difficulty;
import nu.mrpi.wordfeudsolver.domain.GameInfo;
import nu.mrpi.wordfeudsolver.domain.PlayerStats;
import nu.mrpi.wordfeudsolver.domain.PlayerStatsBuilder;
import nu.mrpi.wordfeudsolver.persistance.GameDAO;
import nu.mrpi.wordfeudsolver.persistance.redis.GameDAORedis;
import nu.mrpi.wordfeudsolver.service.SettingsService;
import org.apache.log4j.Logger;

public class StatsBuilderMain {
  private static final Logger LOG = Logger.getLogger(StatsBuilderMain.class);

  private static GameDAO gameDAO;

  public static void main(String[] args) {
    final SettingsService settingsService = new SettingsService();
    gameDAO = new GameDAORedis(settingsService);

    final List<GameInfo> allGameInfos = gameDAO.getAllGameInfos();

    final Map<String, PlayerStats> playerStatsMap = buildStats(allGameInfos);

    playerStatsMap.forEach((s, playerStats) -> gameDAO.updatePlayerStats(playerStats));
  }

  private static Map<String, PlayerStats> buildStats(final List<GameInfo> allGameInfos) {
    Map<String, PlayerStats> allPlayerStats = new HashMap<>();

    for (GameInfo gameInfo : allGameInfos) {
      if (!gameInfo.isDifficultySet() || !gameInfo.isGameOver()) {
        continue;
      }
      LOG.info("Processing " + gameInfo);

      final String player = gameInfo.getOpponent();

      PlayerStats playerStats;
      if (!allPlayerStats.containsKey(player)) {
        playerStats = new PlayerStatsBuilder()
            .player(player)
            .gameStats(new EnumMap<>(Difficulty.class))
            .build();
        allPlayerStats.put(player, playerStats);
      } else {
        playerStats = allPlayerStats.get(player);
      }

      allPlayerStats.put(player, addGameToPlayerStats(gameInfo, playerStats));
    }

    return allPlayerStats;
  }
}
