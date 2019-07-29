package nu.mrpi.wordfeudsolver.worker;

import nu.mrpi.wordfeudapi.WordFeudClient;
import nu.mrpi.wordfeudapi.domain.Game;
import nu.mrpi.wordfeudsolver.domain.GameInfo;
import nu.mrpi.wordfeudsolver.persistance.GameNotFoundException;
import nu.mrpi.wordfeudsolver.service.GameService;

public class EndGameWorker extends AbstractWorker {
  private final WordFeudClient wordFeudClient;
  private final GameService gameService;

  EndGameWorker(final WordFeudClient wordFeudClient, final GameService gameService) {
    this.wordFeudClient = wordFeudClient;
    this.gameService = gameService;
  }

  @Override
  public void doWork() {
    for (Game game : wordFeudClient.getGames()) {
      try {
        final GameInfo gameInfo = gameService.getGameInfo(game);

        if (gameInfo.getEndGame() != game.getEndGame()) {
          gameService.storeGameInfo(game);
        }
      } catch (GameNotFoundException e) {
        // Skipping
      }
    }
  }
}
