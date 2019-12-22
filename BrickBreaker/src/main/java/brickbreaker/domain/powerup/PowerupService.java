package brickbreaker.domain.powerup;

import java.util.Random;
import javafx.geometry.Point2D;

public class PowerupService {

    private Random random;
    private int dropChance;
    private PowerupType[] types;
    private boolean[] active;
    private boolean[] onScreen;

    /**
     * Creates a new PowerupService that manages powerup spawning.
     *
     * @param random the Random object used for random rolls
     * @param dropChance the drop chance of powerups is 1/dropChance
     */
    public PowerupService(Random random, int dropChance) {
        this.random = random;
        this.dropChance = dropChance;
        types = PowerupType.values();
        active = new boolean[types.length];
        onScreen = new boolean[types.length];
    }

    /**
     * Roll for a chance of spawning a powerup. Powerups that are active or
     * on-screen cannot spawn. Chance to spawn is based on dropChance set in
     * constructor.
     *
     * @param x the x-coordinate of spawn point
     * @param y the y-coordinate of spawn point
     * @return the powerup if roll was successful, otherwise null
     * @see brickbreaker.domain.powerup.Powerup
     */
    public Powerup roll(double x, double y) {
        if (random.nextInt(dropChance) != 0) {
            return null;
        }
        int type = random.nextInt(types.length);
        if (active[type] || onScreen[type]) {
            return null;
        }
        onScreen[type] = true;
        Point2D movement = new Point2D(0, 200);
        Powerup powerup = new Powerup(x - 20, y, 40, 15, movement, types[type]);
        return powerup;
    }

    /**
     * Makes every powerup available again.
     */
    public void reset() {
        active = new boolean[active.length];
        onScreen = new boolean[onScreen.length];
    }

    public boolean isActive(PowerupType type) {
        return active[type.ordinal()];
    }

    /**
     * Marks powerup type as active if it is limited.
     *
     * @param type the type to set active
     * @see brickbreaker.domain.powerup.PowerupType
     */
    public void setActive(PowerupType type) {
        outOfBounds(type);
        active[type.ordinal()] = type.isLimited();
    }

    /**
     * Marks powerup type as not being on screen anymore.
     *
     * @param type the type of powerup
     * @see brickbreaker.domain.powerup.PowerupType
     */
    public void outOfBounds(PowerupType type) {
        onScreen[type.ordinal()] = false;
    }

}
