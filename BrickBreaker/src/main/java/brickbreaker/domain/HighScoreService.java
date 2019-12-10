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

    /**
     * Creates a new HighScoreService that uses a HighScoreDao to save and fetch
     * high scores. The amount of scores kept is decided by scoreCount. If
     * scores don't exists when HighScoreService is created. A list of
     * placeholder scores will be made.
     *
     * @param scoreDao the HighScoreDao used to save and fetch scores
     * @param scoreCount the maximum amount of scores saved
     * @see brickbreaker.dao.HighScoreDao
     */
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

    /**
     * Checks if score is better than the lowest high score.
     *
     * @param score the score to check
     * @return true if score is better, otherwise false
     */
    public boolean isHighScore(int score) {
        return score > scores.get(scoreCount - 1).getScore();
    }

    /**
     * Adds score to list of high scores. Removes the lowest score to keep
     * length constant. Saves the result with the HighScoreDao object provided
     * in constructor.
     *
     * @param name the name associated with the score
     * @param score the score
     * @see brickbreaker.dao.HighScoreDao
     */
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
