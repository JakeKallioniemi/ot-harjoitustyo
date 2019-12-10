package brickbreaker.domain;

import java.util.Random;
import javafx.geometry.Point2D;

public class PowerupService {

    private Random random;
    private int dropChance;
    private PowerupType[] types;
    private boolean[] active;
    private boolean[] onScreen;

    public PowerupService(Random random, int dropChance) {
        this.random = random;
        this.dropChance = dropChance;
        types = PowerupType.values();
        active = new boolean[types.length];
        onScreen = new boolean[types.length];
    }

    public Powerup roll(double x, double y) {
        if (random.nextInt(dropChance) == 0) {
            int type = random.nextInt(types.length);
            if (active[type] || onScreen[type]) {
                return null;
            }
            onScreen[type] = true;
            Point2D movement = new Point2D(0, 200);
            Powerup powerup = new Powerup(x - 20, y, 40, 15, movement, types[type]);
            return powerup;
        }
        return null;
    }

    public void reset() {
        active = new boolean[active.length];
        onScreen = new boolean[onScreen.length];
    }

    public boolean isActive(PowerupType type) {
        return active[type.ordinal()];
    }

    public void setActive(PowerupType type) {
        outOfBounds(type);
        active[type.ordinal()] = type.isLimited();
    }

    public void outOfBounds(PowerupType type) {
        onScreen[type.ordinal()] = false;
    }

}
