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
    private int newestIndex;

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
        newestIndex = -1;
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
     * Returns the index of the most recent score and then resets
     * it to default value.
     * 
     * @return index of the most recent score or -1 if no new score
     */
    public int getNewestScoreIndex() {
        int temp = newestIndex;
        newestIndex = -1;
        return temp;
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
        HighScoreEntry newScore = new HighScoreEntry(name, score);
        scores.add(newScore);
        Collections.sort(scores);
        scores.remove(scoreCount);
        newestIndex = scores.indexOf(newScore);
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
