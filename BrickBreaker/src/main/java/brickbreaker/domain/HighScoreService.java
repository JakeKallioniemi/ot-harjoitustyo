package brickbreaker.domain;

import brickbreaker.dao.HighScoreDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoreService {

    private HighScoreDao scoreDao;
    private List<HighScoreEntry> scores;
    private int scoreCount;

    public HighScoreService(HighScoreDao scoreDao, int scoreCount) {
        this.scoreDao = scoreDao;
        this.scoreCount = scoreCount;
        try {
            scores = scoreDao.list();
            if (scores.isEmpty()) {
                initScores();
            }
        } catch (IOException ex) {
            initScores();
        }
    }

    public List<HighScoreEntry> getScores() {
        return scores;
    }

    public boolean isHighScore(int score) {
        return score > scores.get(scoreCount - 1).getScore();
    }

    public void addScore(String name, int score) {
        scores.add(new HighScoreEntry(name, score));
        Collections.sort(scores);
        scores.remove(scoreCount);
        try {
            scoreDao.add(scores);
        } catch (IOException ex) {
            // just printing for now
            ex.printStackTrace();
        }
    }

    private void initScores() {
        List<HighScoreEntry> placeholderScores = new ArrayList<>();
        for (int i = 1; i <= scoreCount; i++) {
            placeholderScores.add(new HighScoreEntry("CPU", 1000 * i));
        }
        Collections.sort(placeholderScores);
        scores = placeholderScores;
        try {
            scoreDao.add(placeholderScores);
        } catch (IOException ex) {
            // just printing for now
            ex.printStackTrace();
        }
    }
}
