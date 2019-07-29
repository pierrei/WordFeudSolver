package nu.mrpi.wordfeudsolver.solver.filter;

import java.util.List;
import nu.mrpi.wordfeudapi.domain.TileMove;

public class LengthFilter implements TileMoveFilter {

  private final boolean shortestFirst;

  LengthFilter(final boolean shortestFirst) {
    this.shortestFirst = shortestFirst;
  }

  @Override
  public List<TileMove> filter(final List<TileMove> tileMoves) {
    tileMoves.sort((o1, o2) -> {
      if (shortestFirst) {
        return o1.getTiles().length - o2.getTiles().length;
      } else {
        return o2.getTiles().length - o1.getTiles().length;
      }
    });
    return tileMoves;
  }
}
