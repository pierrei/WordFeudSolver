package nu.mrpi.wordfeudsolver.solver;

import nu.mrpi.wordfeudapi.domain.Rack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pierre Ingmansson
 */
public class TilesInRack {
    private List<Character> remainingCharacters;

    public TilesInRack(Rack rack) {
        this.remainingCharacters = new ArrayList<Character>();
        for (char c : rack.chars()) {
            remainingCharacters.add(c);
        }
    }

    public boolean isCharTaken(char character) {
        return !remainingCharacters.remove(Character.valueOf(character));
    }
}
