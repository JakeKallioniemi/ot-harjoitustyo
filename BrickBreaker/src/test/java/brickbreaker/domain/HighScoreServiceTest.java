package brickbreaker.domain;

import brickbreaker.domain.mocks.MockHighScoreDao;
import brickbreaker.dao.HighScoreDao;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HighScoreServiceTest {

    private HighScoreDao scoreDao;
    private HighScoreService scoreService;

    @Before
    public void setUp() {
        scoreDao = new MockHighScoreDao();
        scoreService = new HighScoreService(scoreDao, 10);
    }

    @Test
    public void scoresInitWhenTheyDoNotExists() {
        List<HighScoreEntry> scores = scoreService.getScores();
        assertEquals(10, scores.size());
        int s = 10000;
        for (HighScoreEntry score : scores) {
            assertEquals("CPU", score.getName());
            assertEquals(s, score.getScore());
            s -= 1000;
        }
    }

    @Test
    public void scoreIsAdded() {
        scoreService.addScore("ABC", 3500);
        HighScoreEntry entry = scoreService.getScores().get(7);
        assertEquals("ABC", entry.getName());
        assertEquals(3500, entry.getScore());
    }

    @Test
    public void scoreCountStaysTheSame() {
        scoreService.addScore("ABC", 3500);
        assertEquals(10, scoreService.getScores().size());
    }

    @Test
    public void correctScoreIsRemoved() {
        scoreService.addScore("ABC", 3500);
        HighScoreEntry entry = scoreService.getScores().get(9);
        assertEquals(2000, entry.getScore());
    }

    @Test
    public void highScoreIsDetected() {
        assertTrue(scoreService.isHighScore(1001));
        assertTrue(scoreService.isHighScore(100000));
    }

    @Test
    public void smallScoreIsRejected() {
        assertFalse(scoreService.isHighScore(999));
    }

    @Test
    public void scoresAreNotOverwritten() {
        scoreService.addScore("ABC", 3500);
        scoreService = new HighScoreService(scoreDao, 10);
        HighScoreEntry entry = scoreService.getScores().get(7);
        assertEquals("ABC", entry.getName());
        assertEquals(3500, entry.getScore());
    }
}
