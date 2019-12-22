package brickbreaker.domain;

import brickbreaker.domain.powerup.PowerupType;
import brickbreaker.domain.powerup.PowerupService;
import brickbreaker.domain.powerup.Powerup;
import brickbreaker.domain.mocks.MockRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PowerupServiceTest {

    private PowerupService service;
    private List<Integer> values;

    @Before
    public void setUp() {
        values = new ArrayList<>();
        Random random = new MockRandom(values);
        service = new PowerupService(random, 10);
    }

    @Test
    public void nullWhenRollFails() {
        values.add(1);
        Powerup powerup = service.roll(0, 0);
        assertNull(powerup);;
    }

    @Test
    public void powerupReturnedWhenRollSuccesful() {
        values.addAll(Arrays.asList(0, 1));
        Powerup powerup = service.roll(0, 0);
        assertNotNull(powerup);
    }

    @Test
    public void returnedPowerupCorrectProperties() {
        values.addAll(Arrays.asList(0, 1));
        Powerup powerup = service.roll(50, 35);
        assertEquals(30, powerup.getX(), 0.01);
        assertEquals(35, powerup.getY(), 0.01);
        assertEquals(PowerupType.WIDE, powerup.getType());
    }

    @Test
    public void limitedPowerupActivates() {
        service.setActive(PowerupType.WIDE);
        assertTrue(service.isActive(PowerupType.WIDE));
    }

    @Test
    public void unlimitedPowerupDoesNotActivate() {
        service.setActive(PowerupType.HEALTH);
        assertFalse(service.isActive(PowerupType.HEALTH));
    }

    @Test
    public void powerupOnScreenDoesNotDrop() {
        values.addAll(Arrays.asList(0, 2, 0 ,2));
        service.roll(0, 0);
        Powerup powerup = service.roll(0, 0);
        assertNull(powerup);
    }

    @Test
    public void activePowerupDoesNotDrop() {
        service.setActive(PowerupType.WIDE);
        values.addAll(Arrays.asList(0, 1));
        Powerup powerup = service.roll(0, 0);
        assertNull(powerup);
    }

    @Test
    public void activeStatusResets() {
        service.setActive(PowerupType.WIDE);
        service.setActive(PowerupType.SUPER);
        service.reset();
        assertFalse(service.isActive(PowerupType.WIDE));
        assertFalse(service.isActive(PowerupType.SUPER));
    }
}
