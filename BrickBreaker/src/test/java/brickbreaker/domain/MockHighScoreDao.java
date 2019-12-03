package brickbreaker.domain;

import brickbreaker.dao.HighScoreDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MockHighScoreDao implements HighScoreDao {

    private List<HighScoreEntry> scores;

    public MockHighScoreDao() {
        scores = new ArrayList<>();
    }

    @Override
    public List<HighScoreEntry> list() throws IOException {
        return scores;
    }

    @Override
    public void add(List<HighScoreEntry> scores) throws IOException {
        this.scores = scores;
    }

}
