package brickbreaker.domain;

import javafx.scene.shape.Rectangle;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PaddleTest {

    Paddle paddle;

    @Before
    public void setUp() {
        paddle = new Paddle(190, 30);
    }

    @Test
    public void correctStartingPosition() {
        assertEquals(545, paddle.getX(), 0.01);
        assertEquals(680, paddle.getY(), 0.01);
    }

    @Test
    public void correctShape() {
        assertEquals(Rectangle.class, paddle.getShape().getClass());
    }

    @Test
    public void correctSize() {
        assertEquals(190, paddle.getWidth(), 0.01);
        assertEquals(30, paddle.getHeight(), 0.01);
    }

    @Test
    public void moveMovesPaddleByGivenAmount() {
        paddle.move(100.5, 1);
        assertEquals(645.5, paddle.getX(), 0.01);
    }

    @Test
    public void deltaTimeChangesMovedDistance() {
        paddle.move(100, 0.5);
        assertEquals(595, paddle.getX(), 0.01);
    }

    @Test
    public void moveStaysInBoundsLeftSide() {
        paddle.move(-1000, 1);
        assertEquals(0, paddle.getX(), 0.01);
    }

    @Test
    public void moveStaysInBoundsRightSide() {
        paddle.move(1000, 1);
        assertEquals(1090, paddle.getX(), 0.01);
    }

    @Test
    public void positionResets() {
        paddle.move(200, 1);
        paddle.resetPosition();
        assertEquals(545, paddle.getX(), 0.01);
        assertEquals(680, paddle.getY(), 0.01);
    }

    // testing superclass method here because it's abstract and can't be instantiated
    @Test
    public void collisionDetectedWhenShapesOverlap() {
        Paddle other = new Paddle(190, 30);
        assertTrue(paddle.intersects(other));
    }

    @Test
    public void noCollisionWhenEdgesTouch() {
        Paddle other = new Paddle(190, 30);
        other.move(190, 1);
        assertFalse(paddle.intersects(other));
    }
}
