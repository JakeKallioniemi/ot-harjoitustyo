package brickbreaker.domain;

import javafx.scene.shape.Rectangle;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BrickTest {

    Brick brick;

    @Before
    public void setUp() {
        brick = new Brick(10, 20, 105, 30, 2, true, 1);
    }

    @Test
    public void correctStartingPosition() {
        assertEquals(10, brick.getX(), 0.01);
        assertEquals(20, brick.getY(), 0.01);
    }

    @Test
    public void correctShape() {
        assertEquals(Rectangle.class, brick.getShape().getClass());
    }

    @Test
    public void correctSize() {
        assertEquals(105, brick.getWidth(), 0.01);
        assertEquals(30, brick.getHeight(), 0.01);
    }
    
    @Test
    public void correctType() {
        assertEquals(2, brick.getType());
    }
    
    @Test
    public void hitsLeftReduced() {
        brick.hit();
        assertEquals(0, brick.hitsLeft());
    }
}
