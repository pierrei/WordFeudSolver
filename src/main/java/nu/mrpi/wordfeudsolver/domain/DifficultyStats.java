package nu.mrpi.wordfeudsolver.domain;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface DifficultyStats {
  Difficulty difficulty();
  int wins();
  int losses();
  int maxWin();
  int minWin();
  int maxLoss();
  int minLoss();
}
