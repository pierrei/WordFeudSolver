package nu.mrpi.wordfeudsolver;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import nu.mrpi.wordfeudapi.RestWordFeudClient;
import nu.mrpi.wordfeudapi.WordFeudClient;
import nu.mrpi.wordfeudapi.domain.EndGame;
import nu.mrpi.wordfeudapi.domain.Game;
import nu.mrpi.wordfeudsolver.domain.GameInfo;
import nu.mrpi.wordfeudsolver.domain.WFAccount;
import nu.mrpi.wordfeudsolver.persistance.GameDAO;
import nu.mrpi.wordfeudsolver.persistance.redis.GameDAORedis;
import nu.mrpi.wordfeudsolver.service.SettingsService;
import org.apache.log4j.Logger;

public class EndGameUpdateMain {

  private static final Logger LOG = Logger.getLogger(EndGameUpdateMain.class);

  private static GameDAO gameDAO;
  private static WordFeudClient wordFeudClient;

  public static void main(String[] args) {
    final SettingsService settingsService = new SettingsService();
    wordFeudClient = new RestWordFeudClient();
    final WFAccount firstAccount = settingsService.getWordFeudAccounts().get(0);
    wordFeudClient.logon(firstAccount.getEmail(), firstAccount.getPassword());
    gameDAO = new GameDAORedis(settingsService);

    updateOldGameInfoWithEndGameStatus();
  }

  private static void updateOldGameInfoWithEndGameStatus() {
    final Set<Long> currentOngoingGameIds =
        Arrays.stream(wordFeudClient.getGames()).map(Game::getId)
            .collect(Collectors.toSet());

    for (GameInfo gameInfo : gameDAO.getAllGameInfos()) {
      if (!currentOngoingGameIds.contains(gameInfo.getGameId())) {
        gameInfo.setEndGame(EndGame.OverUnknown);
        gameDAO.updateGameInfo(gameInfo);
      }
    }
  }
}
