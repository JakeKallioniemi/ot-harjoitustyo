package brickbreaker.domain;

import brickbreaker.domain.powerup.PowerupService;
import brickbreaker.domain.powerup.Powerup;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    private Game game;
    private List<Ball> balls;
    private List<Brick> bricks;
    private List<Powerup> powerups;

    @Before
    public void setUp() {
        Random random = new Random();
        balls = new ArrayList<>();
        bricks = new ArrayList<>();
        powerups = new ArrayList<>();
        game = new Game(
                balls, bricks, powerups,
                new PowerupService(random, 8),
                new LevelGenerator(13, 8, 98, 30, random)
        );
    }

    @Test
    public void correctStartingValues() {
        assertEquals(0, game.getScore());
        assertEquals(1, game.getLevel());
        assertEquals(3, game.getLives());
    }
    
    @Test
    public void ballStartingMovementSet() {
        game.start();
        assertNotEquals(Point2D.ZERO, balls.get(0).getMovement());
    }

    @Test
    public void paddleIsMoved() {
        game.movePaddle(-1, 1);
        assertEquals(0, game.getPaddle().getX(), 0.01);
    }

    @Test
    public void paddleIsMoved2() {
        game.movePaddle(1, 1);
        assertEquals(1130, game.getPaddle().getX(), 0.01);
    }

    @Test
    public void ballFollowsPaddle() {
        game.movePaddle(-545, 1);
        game.centerBallOnPaddle();
        assertEquals(75, balls.get(0).getX(), 0.01);
        assertEquals(670, balls.get(0).getY(), 0.01);
    }

    @Test
    public void livesAreReducedWhenBallNotInPlay() {
        balls.get(0).setPosition(0, 2000);
        game.update(1);
        assertFalse(game.inPlay());
        assertEquals(2, game.getLives());
    }

    @Test
    public void livesAreNotReducedWhenBallInPlay() {
        balls.get(0).setPosition(100, 100);
        assertTrue(game.inPlay());
        assertEquals(3, game.getLives());
    }

    @Test
    public void gameOverDetected() {
        balls.get(0).setPosition(0, 2000);
        game.update(1);
        game.inPlay();
        game.inPlay();
        game.inPlay();
        assertTrue(game.isOver());
    }

    @Test
    public void gameNotOverWhenLivesLeft() {
        balls.get(0).setPosition(0, 2000);
        game.inPlay();
        assertFalse(game.isOver());
    }

    @Test
    public void levelIncreases() {
        game.newLevel();
        assertEquals(2, game.getLevel());
    }

    @Test
    public void notEmptyNewLevel() {
        game.newLevel();
        assertFalse(bricks.isEmpty());
        assertFalse(balls.isEmpty());
    }
}
