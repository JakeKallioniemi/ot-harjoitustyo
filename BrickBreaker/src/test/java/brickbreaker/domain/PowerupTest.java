package brickbreaker.domain;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PowerupTest {

    Powerup powerup;

    @Before
    public void setUp() {
        powerup = new Powerup(500, 200, 40, 15, new Point2D(0, 100), PowerupType.EXTRA);
    }

    @Test
    public void correctStartingPosition() {
        assertEquals(500, powerup.getX(), 0.01);
        assertEquals(200, powerup.getY(), 0.01);
    }

    @Test
    public void correctShape() {
        assertEquals(Rectangle.class, powerup.getShape().getClass());
    }

    @Test
    public void correctSize() {
        assertEquals(40, powerup.getWidth(), 0.01);
        assertEquals(15, powerup.getHeight(), 0.01);
    }

    @Test
    public void correctType() {
        assertEquals(PowerupType.EXTRA, powerup.getType());
    }
    
    @Test
    public void startingMovementSet() {
        Point2D movement = powerup.getMovement();
        assertEquals(0, movement.getX(), 0.01);
        assertEquals(100, movement.getY(), 0.01);
    }

    @Test
    public void powerupMoves() {
        powerup.move(1);
        assertEquals(500, powerup.getX(), 0.01);
        assertEquals(300, powerup.getY(), 0.01);
    }

    @Test
    public void moveUsesDt() {
        powerup.move(0.5);
        assertEquals(500, powerup.getX(), 0.01);
        assertEquals(250, powerup.getY(), 0.01);
    }
    
    @Test
    public void inPlayDetected() {
        assertTrue(powerup.inPlay());
    }
    
    @Test
    public void outOfBoundsDetected() {
        powerup = new Powerup(500, 800, 40, 15, new Point2D(0, 100), PowerupType.EXTRA);
        assertFalse(powerup.inPlay());
    }
    
    @Test
    public void powerupTypesLimitsCorrect() {
        assertFalse(PowerupType.EXTRA.isLimited());
        assertTrue(PowerupType.WIDE.isLimited());
        assertTrue(PowerupType.SUPER.isLimited());
        assertFalse(PowerupType.HEALTH.isLimited());
    }
}
