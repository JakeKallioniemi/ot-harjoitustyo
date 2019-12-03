package brickbreaker.domain;

import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    private Game game;

    @Before
    public void setUp() {
        game = new Game();
        game.newLevel();
    }

    @Test
    public void correctStartingValues() {
        assertEquals(0, game.getScore());
        assertEquals(1, game.getLevel());
        assertEquals(3, game.getLives());
    }

    @Test
    public void entitiesExists() {
        assertNotNull(game.getBall());
        assertNotNull(game.getPaddle());
        assertNotNull(game.getBricks());
    }

    @Test
    public void ballStartingMovementSet() {
        game.start();
        assertNotEquals(Point2D.ZERO, game.getBall().getMovement());
    }

    @Test
    public void paddleIsMoved() {
        game.movePaddle(-545, 1);
        assertEquals(0, game.getPaddle().getX(), 0.01);
    }

    @Test
    public void ballFollowsPaddle() {
        game.movePaddle(-545, 1);
        game.resetBall();
        assertEquals(95, game.getBall().getX(), 0.01);
        assertEquals(665, game.getBall().getY(), 0.01);
    }

    @Test
    public void livesAreReducedWhenBallNotInPlay() {
        game.getBall().setPosition(0, 2000);
        assertFalse(game.ballInPlay());
        assertEquals(2, game.getLives());
    }

    @Test
    public void livesAreNotReducedWhenBallInPlay() {
        game.getBall().setPosition(100, 100);
        assertTrue(game.ballInPlay());
        assertEquals(3, game.getLives());
    }

    @Test
    public void gameOverDetected() {
        game.getBall().setPosition(0, 2000);
        game.ballInPlay();
        game.ballInPlay();
        game.ballInPlay();
        assertTrue(game.isOver());
    }

    @Test
    public void gameNotOverWhenLivesLeft() {
        game.getBall().setPosition(0, 2000);
        game.ballInPlay();
        assertFalse(game.isOver());
    }

    // TODO: more tests
}
