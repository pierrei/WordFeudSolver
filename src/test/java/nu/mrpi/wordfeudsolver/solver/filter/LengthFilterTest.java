package nu.mrpi.wordfeudsolver.solver.filter;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import nu.mrpi.wordfeudapi.domain.Tile;
import nu.mrpi.wordfeudapi.domain.TileMove;
import org.junit.Before;
import org.junit.Test;

public class LengthFilterTest {

  private List<TileMove> tileMoves;

  @Before
  public void setUp() {
    tileMoves = Arrays.asList(
        createTileMove("longestwordinthedeck"),
        createTileMove("shortest"),
        createTileMove("inbetween"));
  }

  private TileMove createTileMove(final String word) {
    return new TileMove(
        word.chars().mapToObj(i -> new Tile(0, 0, (char) i, false)).toArray(Tile[]::new),
        word, 0, true);
  }

  @Test
  public void sortsShortestFirst() {
    final LengthFilter lengthFilter = new LengthFilter(true);

    final List<TileMove> filteredTileMoves = lengthFilter.filter(tileMoves);

    assertEquals(3, filteredTileMoves.size());
    assertEquals("shortest", filteredTileMoves.get(0).getWord());
    assertEquals("inbetween", filteredTileMoves.get(1).getWord());
    assertEquals("longestwordinthedeck", filteredTileMoves.get(2).getWord());
  }

  @Test
  public void sortsLongestFirst() {
    final LengthFilter lengthFilter = new LengthFilter(false);

    final List<TileMove> filteredTileMoves = lengthFilter.filter(tileMoves);

    assertEquals(3, filteredTileMoves.size());
    assertEquals("longestwordinthedeck", filteredTileMoves.get(0).getWord());
    assertEquals("inbetween", filteredTileMoves.get(1).getWord());
    assertEquals("shortest", filteredTileMoves.get(2).getWord());
  }
}