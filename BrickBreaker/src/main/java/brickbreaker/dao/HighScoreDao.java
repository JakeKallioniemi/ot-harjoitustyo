package brickbreaker.dao;

import brickbreaker.domain.HighScoreEntry;
import java.io.IOException;
import java.util.List;

public interface HighScoreDao {

    public List<HighScoreEntry> list() throws IOException;
    public void add(List<HighScoreEntry> scores) throws IOException;
}
