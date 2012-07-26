package nu.mrpi.wordfeudsolver.persistance;

import nu.mrpi.wordfeudsolver.domain.GameInfo;

/**
 * @author Pierre Ingmansson (pierre.ingmansson@wirelesscar.biz)
 */
public interface GameDAO {
    void updateGameInfo(GameInfo game);

    GameInfo getGameInfo(long gameId) throws GameNotFoundException;
}
