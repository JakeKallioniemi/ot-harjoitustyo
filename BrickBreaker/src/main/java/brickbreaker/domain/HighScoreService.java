package brickbreaker.domain;

import brickbreaker.dao.HighScoreDao;
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
        scores = scoreDao.list().subList(0, scoreCount);
        if (scores.isEmpty()) {
            initScores();
        }
    }
    
    public boolean isHighScore(int score) {
       return score > scores.get(scoreCount - 1).getScore();
    }
    
    public void addScore(String name, int score) {
        scores.add(new HighScoreEntry(name, score));
        Collections.sort(scores);
        scores.remove(scoreCount);
        scoreDao.add(scores);
    }
    
    private void initScores() {
        List<HighScoreEntry> placeholderScores = new ArrayList<>();
        for (int i = 1; i <= scoreCount; i++) {
            placeholderScores.add(new HighScoreEntry("CPU", 1000 * i));
        }
        scores = placeholderScores;
        scoreDao.add(placeholderScores);
    }
}
