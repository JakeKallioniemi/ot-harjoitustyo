package brickbreaker.domain;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    private Game game;

    @Before
    public void setUp() {
        game = new Game(
                new ArrayList<Ball>(),
                new ArrayList<Brick>(),
                new ArrayList<Powerup>()
        );
    }

    @Test
    public void correctStartingValues() {
        assertEquals(0, game.getScore());
        assertEquals(1, game.getLevel());
        assertEquals(3, game.getLives());
    }

    @Test
    public void entitiesExists() {
        assertNotNull(game.getBalls());
        assertNotNull(game.getPaddle());
        assertNotNull(game.getBricks());
    }

    @Test
    public void ballStartingMovementSet() {
        game.start();
        assertNotEquals(Point2D.ZERO, game.getBalls().get(0).getMovement());
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
        game.resetBall();
        assertEquals(75, game.getBalls().get(0).getX(), 0.01);
        assertEquals(670, game.getBalls().get(0).getY(), 0.01);
    }

    @Test
    public void livesAreReducedWhenBallNotInPlay() {
        game.getBalls().get(0).setPosition(0, 2000);
        game.moveBalls(1);
        assertFalse(game.inPlay());
        assertEquals(2, game.getLives());
    }

    @Test
    public void livesAreNotReducedWhenBallInPlay() {
        game.getBalls().get(0).setPosition(100, 100);
        assertTrue(game.inPlay());
        assertEquals(3, game.getLives());
    }

    @Test
    public void gameOverDetected() {
        game.getBalls().get(0).setPosition(0, 2000);
        game.moveBalls(1);
        game.inPlay();
        game.inPlay();
        game.inPlay();
        assertTrue(game.isOver());
    }

    @Test
    public void gameNotOverWhenLivesLeft() {
        game.getBalls().get(0).setPosition(0, 2000);
        game.inPlay();
        assertFalse(game.isOver());
    }

    @Test
    public void activateExtra() {
        game.activatePower(PowerupType.EXTRA);
        assertEquals(3, game.getBalls().size());
    }

    @Test
    public void activateWide() {
        game.activatePower(PowerupType.WIDE);
        assertEquals(250, game.getPaddle().getWidth(), 0.01);
    }

    @Test
    public void activateSuperBall() {
        game.activatePower(PowerupType.SUPER);
        assertEquals(17, game.getBalls().get(0).getRadius(), 0.01);
        assertTrue(game.getBalls().get(0).isUnstoppable());
    }

    @Test
    public void activateHealth() {
        game.activatePower(PowerupType.HEALTH);
        assertEquals(4, game.getLives());
    }

    @Test
    public void levelIncreases() {
        game.newLevel();
        assertEquals(2, game.getLevel());
    }

    @Test
    public void notEmptyNewLevel() {
        game.newLevel();
        assertFalse(game.getBricks().isEmpty());
        assertFalse(game.getBalls().isEmpty());
    }
    // TODO: more tests
}
