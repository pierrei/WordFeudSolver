package nu.mrpi.wordfeudsolver;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nu.mrpi.wordfeudsolver.domain.Difficulty;
import nu.mrpi.wordfeudsolver.domain.DifficultyStats;
import nu.mrpi.wordfeudsolver.domain.DifficultyStatsBuilder;
import nu.mrpi.wordfeudsolver.domain.GameInfo;
import nu.mrpi.wordfeudsolver.domain.PlayerStats;
import nu.mrpi.wordfeudsolver.domain.PlayerStatsBuilder;
import nu.mrpi.wordfeudsolver.persistance.GameDAO;
import nu.mrpi.wordfeudsolver.persistance.redis.GameDAORedis;
import nu.mrpi.wordfeudsolver.service.SettingsService;
import org.apache.log4j.Logger;

public class StatsBuilderMain {
  private static final Logger LOG = Logger.getLogger(StatsBuilderMain.class);

  private static SettingsService settingsService;
  private static GameDAO gameDAO;

  public static void main(String[] args) {
    settingsService = new SettingsService();
    gameDAO = new GameDAORedis(settingsService);

    final List<GameInfo> allGameInfos = gameDAO.getAllGameInfos();

    final Map<String, PlayerStats> playerStatsMap = buildStats(allGameInfos);

    System.out.println(playerStatsMap);
  }

  private static Map<String, PlayerStats> buildStats(final List<GameInfo> allGameInfos) {
    Map<String, PlayerStats> allPlayerStats = new HashMap<>();

    for (GameInfo gameInfo : allGameInfos) {
      if (!gameInfo.isDifficultySet()) {
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

      DifficultyStats difficultyStats;
      if (!playerStats.gameStats().containsKey(gameInfo.getDifficulty())) {
        difficultyStats = updateWithNewGameInfo(gameInfo, new DifficultyStatsBuilder()
            .difficulty(gameInfo.getDifficulty())
            .minLoss(Integer.MAX_VALUE)
            .maxLoss(0)
            .minWin(Integer.MAX_VALUE)
            .maxWin(0)
            .wins(0)
            .losses(0)).build();
      } else {
        difficultyStats =
            addOneMoreStat(gameInfo, playerStats.gameStats().get(gameInfo.getDifficulty()));
      }

      Map<Difficulty, DifficultyStats> difficultyStatsMap = new HashMap<>(playerStats.gameStats());
      difficultyStatsMap.put(gameInfo.getDifficulty(), difficultyStats);

      allPlayerStats.put(player,
          PlayerStatsBuilder
              .from(playerStats)
              .gameStats(difficultyStatsMap)
              .build());
    }

    return allPlayerStats;
  }

  private static DifficultyStats addOneMoreStat(final GameInfo gameInfo,
                                                final DifficultyStats difficultyStats) {
    return updateWithNewGameInfo(gameInfo, DifficultyStatsBuilder.from(difficultyStats)).build();
  }

  private static DifficultyStatsBuilder updateWithNewGameInfo(final GameInfo gameInfo,
                                                              final DifficultyStatsBuilder difficultyStatsBuilder) {
    if (gameInfo.getBotScore() > gameInfo.getOpponentScore()) {
      difficultyStatsBuilder.losses(difficultyStatsBuilder.losses() + 1);

      final int lossMargin = gameInfo.getBotScore() - gameInfo.getOpponentScore();
      if (lossMargin > difficultyStatsBuilder.maxLoss()) {
        difficultyStatsBuilder.maxLoss(lossMargin);
      }

      if (lossMargin < difficultyStatsBuilder.minLoss()) {
        difficultyStatsBuilder.minLoss(lossMargin);
      }
    } else {
      difficultyStatsBuilder.wins(difficultyStatsBuilder.wins() + 1);

      final int winMargin = gameInfo.getOpponentScore() - gameInfo.getBotScore();
      if (winMargin > difficultyStatsBuilder.maxWin()) {
        difficultyStatsBuilder.maxWin(winMargin);
      }

      if (winMargin < difficultyStatsBuilder.minWin()) {
        difficultyStatsBuilder.minWin(winMargin);
      }
    }
    return difficultyStatsBuilder;
  }
}
