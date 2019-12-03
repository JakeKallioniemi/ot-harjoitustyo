package brickbreaker.dao;

import brickbreaker.domain.HighScoreEntry;
import java.util.List;

public interface HighScoreDao {

    public List<HighScoreEntry> list();
    public void add(List<HighScoreEntry> scores);
}
