package nu.mrpi.wordfeudsolver.domain;

import com.google.gson.Gson;
import io.norberg.automatter.AutoMatter;
import java.util.Map;

@AutoMatter
public interface PlayerStats {

  String player();

  Map<Difficulty, DifficultyStats> gameStats();

  public static PlayerStats fromJson(final String json) {
    return new Gson().fromJson(json, PlayerStats.class);
  }

  default String toJson() {
    return new Gson().toJson(this);
  }

}
