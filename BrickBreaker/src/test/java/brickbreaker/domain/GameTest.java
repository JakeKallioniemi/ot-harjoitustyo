package brickbreaker.domain;

import brickbreaker.domain.mocks.MockRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    private Game game;

    @Before
    public void setUp() {
        Random random = new Random();
        
        game = new Game(
                new ArrayList<Ball>(),
                new ArrayList<Brick>(),
                new ArrayList<Powerup>(),
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
    public void entitiesExists() {
        assertNotNull(game.getBalls());
        assertNotNull(game.getPaddle());
        assertNotNull(game.getBricks());
        assertNotNull(game.getPowerups());
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
        game.centerBallOnPaddle();
        assertEquals(75, game.getBalls().get(0).getX(), 0.01);
        assertEquals(670, game.getBalls().get(0).getY(), 0.01);
    }

    @Test
    public void livesAreReducedWhenBallNotInPlay() {
        game.getBalls().get(0).setPosition(0, 2000);
        game.update(1);
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
        game.update(1);
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

//    @Test
//    public void activateExtra() {
//        game.activatePower(PowerupType.EXTRA);
//        assertEquals(3, game.getBalls().size());
//    }
//
//    @Test
//    public void activateWide() {
//        game.activatePower(PowerupType.WIDE);
//        assertEquals(250, game.getPaddle().getWidth(), 0.01);
//    }
//
//    @Test
//    public void activateSuperBall() {
//        game.activatePower(PowerupType.SUPER);
//        assertEquals(17, game.getBalls().get(0).getRadius(), 0.01);
//        assertTrue(game.getBalls().get(0).isUnstoppable());
//    }
//
//    @Test
//    public void activateHealth() {
//        game.activatePower(PowerupType.HEALTH);
//        assertEquals(4, game.getLives());
//    }

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

//    @Test
//    public void brickIsRemovedWhenHit() {
//        Brick brick = new Brick(0, 0, 10, 10, 1, true, 1);
//        Ball ball = new Ball(10, false);
//        boolean removed = game.brickHit(brick, ball);
//        assertTrue(removed);
//    }
//
//    @Test
//    public void brickIsNotRemovedWhenHealthLeft() {
//        Brick brick = new Brick(0, 0, 10, 10, 1, true, 7);
//        Ball ball = new Ball(10, false);
//        boolean removed = game.brickHit(brick, ball);
//        assertFalse(removed);
//    }
//
//    @Test
//    public void unbreakableBrickIsNotRemoved() {
//        Brick brick = new Brick(0, 0, 10, 10, 1, false, 8);
//        Ball ball = new Ball(10, false);
//        boolean removed = game.brickHit(brick, ball);
//        assertFalse(removed);
//    }
//
//    @Test
//    public void unbreakableBrickIsRemovedWhenBallUnstoppable() {
//        Brick brick = new Brick(0, 0, 10, 10, 1, false, 8);
//        Ball ball = new Ball(10, true);
//        boolean removed = game.brickHit(brick, ball);
//        assertTrue(removed);
//    }
//
//    @Test
//    public void scoreIsUpdated() {
//        Brick brick = new Brick(0, 0, 10, 10, 1, false, 1);
//        Ball ball = new Ball(10, true);
//        game.brickHit(brick, ball);
//        assertEquals(60, game.getScore());
//    }
//
//    @Test
//    public void ballBouncesBrickLeftSide() {
//        Brick brick = new Brick(100, 100, 98, 30, 0, true, 1);
//        Ball ball = new Ball(10, true);
//        ball.setPosition(96, 100);
//        ball.setMovement(new Point2D(300, -300));
//        game.bounceFromBrick(brick, ball);
//        Point2D movement = ball.getMovement();
//        assertEquals(-300, movement.getX(), 0.01);
//        assertEquals(-300, movement.getY(), 0.01);
//    }
//
//    @Test
//    public void ballBouncesBrickRightSide() {
//        Brick brick = new Brick(100, 100, 98, 30, 0, true, 1);
//        Ball ball = new Ball(10, true);
//        ball.setPosition(210, 100);
//        ball.setMovement(new Point2D(-300, -300));
//        game.bounceFromBrick(brick, ball);
//        Point2D movement = ball.getMovement();
//        assertEquals(300, movement.getX(), 0.01);
//        assertEquals(-300, movement.getY(), 0.01);
//    }
//
//    @Test
//    public void ballBouncesBrickTop() {
//        Brick brick = new Brick(100, 100, 98, 30, 0, true, 1);
//        Ball ball = new Ball(10, true);
//        ball.setPosition(100, 96);
//        ball.setMovement(new Point2D(-300, 300));
//        game.bounceFromBrick(brick, ball);
//        Point2D movement = ball.getMovement();
//        assertEquals(-300, movement.getX(), 0.01);
//        assertEquals(-300, movement.getY(), 0.01);
//    }
//
//    @Test
//    public void ballBouncesBrickBottom() {
//        Brick brick = new Brick(100, 100, 98, 30, 0, true, 1);
//        Ball ball = new Ball(10, true);
//        ball.setPosition(100, 104);
//        ball.setMovement(new Point2D(-300, -300));
//        game.bounceFromBrick(brick, ball);
//        Point2D movement = ball.getMovement();
//        assertEquals(-300, movement.getX(), 0.01);
//        assertEquals(300, movement.getY(), 0.01);
//    }
    
//    @Test
//    public void 
}
