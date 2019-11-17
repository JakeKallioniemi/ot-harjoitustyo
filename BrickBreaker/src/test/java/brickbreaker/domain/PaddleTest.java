package brickbreaker.domain;

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
    public void moveMovesPaddleByGivenAmount() {
        paddle.move(100.5);
        assertEquals(645.5, paddle.getX(), 0.01);
    }

    @Test
    public void moveStaysInBoundsLeftSide() {
        paddle.move(-1000);
        assertEquals(0, paddle.getX(), 0.01);
    }

    @Test
    public void moveStaysInBoundsRightSide() {
        paddle.move(1000);
        assertEquals(1090, paddle.getX(), 0.01);
    }
}
