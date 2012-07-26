package nu.mrpi.wordfeudsolver.solver;

import nu.mrpi.wordfeudapi.domain.Rack;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Pierre Ingmansson
 */
public class TilesInRackTest {
    private TilesInRack tilesInRack;

    @Before
    public void setUp() throws Exception {
        tilesInRack = new TilesInRack(new Rack('a', 'b', 'b', 'c'));
    }

    @Test
    public void lastCharIsNotTaken() throws Exception {
        assertFalse(tilesInRack.isCharTaken('c'));
    }

    @Test
    public void oneCharIsTakenSecondTimeAsked() throws Exception {
        tilesInRack.isCharTaken('c');

        assertTrue(tilesInRack.isCharTaken('c'));
    }

    @Test
    public void charNotInListWillAppearTaken() throws Exception {
        assertTrue(tilesInRack.isCharTaken('d'));
    }

    @Test
    public void twoCharsWillReturnTrueButNotThirdTime() throws Exception {
        assertFalse(tilesInRack.isCharTaken('b'));
        assertFalse(tilesInRack.isCharTaken('b'));
        assertTrue(tilesInRack.isCharTaken('b'));
    }
}
