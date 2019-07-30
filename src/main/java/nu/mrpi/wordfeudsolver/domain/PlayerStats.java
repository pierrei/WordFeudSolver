package nu.mrpi.wordfeudsolver.domain;

import io.norberg.automatter.AutoMatter;
import java.util.Map;
import nu.mrpi.wordfeudsolver.GsonUtil;

@AutoMatter
public interface PlayerStats {

  String player();

  Map<Difficulty, DifficultyStats> gameStats();

  public static PlayerStats fromJson(final String json) {
    return GsonUtil.gson().fromJson(json, PlayerStats.class);
  }

  default String toJson() {
    return GsonUtil.gson().toJson(this);
  }

}
