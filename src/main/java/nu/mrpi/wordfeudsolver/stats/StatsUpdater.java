package nu.mrpi.wordfeudsolver.stats;

import java.util.HashMap;
import java.util.Map;
import nu.mrpi.wordfeudsolver.domain.Difficulty;
import nu.mrpi.wordfeudsolver.domain.DifficultyStats;
import nu.mrpi.wordfeudsolver.domain.DifficultyStatsBuilder;
import nu.mrpi.wordfeudsolver.domain.GameInfo;
import nu.mrpi.wordfeudsolver.domain.PlayerStats;
import nu.mrpi.wordfeudsolver.domain.PlayerStatsBuilder;

public class StatsUpdater {

  public static PlayerStats addGameToPlayerStats(final GameInfo gameInfo,
                                                 final PlayerStats playerStats) {
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

    return PlayerStatsBuilder
        .from(playerStats)
        .gameStats(difficultyStatsMap)
        .build();
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
