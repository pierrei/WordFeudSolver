package nu.mrpi.wordfeudsolver.persistance;

import java.util.List;
import nu.mrpi.wordfeudsolver.domain.GameInfo;

/**
 * @author Pierre Ingmansson
 */
public interface GameDAO {
    void updateGameInfo(GameInfo game);

    GameInfo getGameInfo(long gameId) throws GameNotFoundException;

    List<GameInfo> getAllGameInfos();
}
