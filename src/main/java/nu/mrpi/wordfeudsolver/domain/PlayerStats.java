package nu.mrpi.wordfeudsolver.domain;

import io.norberg.automatter.AutoMatter;
import java.util.Map;

@AutoMatter
public interface PlayerStats {

  String player();

  Map<Difficulty, DifficultyStats> gameStats();
}
