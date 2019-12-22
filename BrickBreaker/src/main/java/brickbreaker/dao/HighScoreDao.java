package brickbreaker.dao;

import brickbreaker.domain.highscore.HighScoreEntry;
import java.io.IOException;
import java.util.List;

public interface HighScoreDao {

    public List<HighScoreEntry> list() throws Exception;
    public void add(List<HighScoreEntry> scores) throws IOException;
}
