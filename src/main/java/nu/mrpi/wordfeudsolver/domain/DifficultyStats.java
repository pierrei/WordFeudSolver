package nu.mrpi.wordfeudsolver.domain;

import io.norberg.automatter.AutoMatter;

@AutoMatter
public interface DifficultyStats extends Comparable<DifficultyStats> {
  Difficulty difficulty();
  int wins();
  int losses();
  int ties();
  int maxWin();
  int minWin();
  int maxLoss();
  int minLoss();

  default int compareTo(DifficultyStats o) {
    return difficulty().compareTo(o.difficulty());
  }
}
