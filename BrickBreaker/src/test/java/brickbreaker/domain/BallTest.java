package brickbreaker.domain;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BallTest {

    private Ball ball;

    @Before
    public void setUp() {
        ball = new Ball(15);
    }

    @Test
    public void correctStartingPosition() {
        assertEquals(640, ball.getX(), 0.01);
        assertEquals(665, ball.getY(), 0.01);
    }

    @Test
    public void correctShape() {
        assertEquals(Circle.class, ball.getShape().getClass());
    }

    @Test
    public void correctSize() {
        assertEquals(15, ball.getRadius(), 0.01);
    }

    @Test
    public void stationaryAtStart() {
        assertEquals(Point2D.ZERO, ball.getMovement());
    }

    @Test
    public void movementIsSet() {
        Point2D movement = new Point2D(20, -50);
        ball.setMovement(movement);
        assertEquals(movement, ball.getMovement());
    }

    @Test
    public void moveMovesBall() {
        Point2D movement = new Point2D(20, -50);
        ball.setMovement(movement);
        ball.move(1);
        assertEquals(660, ball.getX(), 0.01);
        assertEquals(615, ball.getY(), 0.01);
    }

    @Test
    public void moveDoesNotMoveBallWhenMovementIsNotSet() {
        ball.move(1);
        assertEquals(640, ball.getX(), 0.01);
        assertEquals(665, ball.getY(), 0.01);
    }

    @Test
    public void ballBouncesLeftSide() {
        Point2D movement = new Point2D(-626, -20);
        ball.setMovement(movement);
        ball.move(1);
        assertEquals(626, ball.getMovement().getX(), 0.01);
        assertEquals(-20, ball.getMovement().getY(), 0.01);
    }

    @Test
    public void ballBouncesRighSide() {
        Point2D movement = new Point2D(626, -20);
        ball.setMovement(movement);
        ball.move(1);
        assertEquals(-626, ball.getMovement().getX(), 0.01);
        assertEquals(-20, ball.getMovement().getY(), 0.01);
    }

    @Test
    public void ballBouncesTop() {
        Point2D movement = new Point2D(-20, -651);
        ball.setMovement(movement);
        ball.move(1);
        assertEquals(-20, ball.getMovement().getX(), 0.01);
        assertEquals(651, ball.getMovement().getY(), 0.01);
    }

    @Test
    public void ballDoesNotBounceBottom() {
        Point2D movement = new Point2D(-20, 200);
        ball.setMovement(movement);
        ball.move(1);
        assertEquals(-20, ball.getMovement().getX(), 0.01);
        assertEquals(200, ball.getMovement().getY(), 0.01);
    }

    @Test
    public void ballInPlayDetected() {
        Point2D movement = new Point2D(-20, -300);
        ball.setMovement(movement);
        ball.move(1);
        assertTrue(ball.inPlay());
    }

    @Test
    public void ballNotInPlayDetected() {
        Point2D movement = new Point2D(-20, 200);
        ball.setMovement(movement);
        ball.move(1);
        assertFalse(ball.inPlay());
    }

    @Test
    public void ballStops() {
        Point2D movement = new Point2D(100, 100);
        ball.setMovement(movement);
        ball.move(1);
        ball.stop();
        assertEquals(Point2D.ZERO, ball.getMovement());
    }

    @Test
    public void ballPositionChangesWhenSet() {
        ball.setPosition(10, 20);
        assertEquals(10, ball.getX(), 0.01);
        assertEquals(20, ball.getY(), 0.01);
    }
}
