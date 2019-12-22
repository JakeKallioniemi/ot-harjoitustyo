package brickbreaker.domain.powerup;

import brickbreaker.domain.Entity;
import static brickbreaker.BrickBreaker.GAME_HEIGHT;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class Powerup extends Entity {

    private PowerupType type;
    private Point2D movement;

    /**
     * Creates a new Powerup that extends Entity. The shape of the Powerup is a
     * Rectangle object.
     *
     * @param x the x-coordinate of the powerup
     * @param y the y-coordinate of the powerup
     * @param width the width of the powerup
     * @param height the height of the powerup
     * @param movement the starting movement of the powerup
     * @param type the type of the powerup
     * @see brickbreaker.domain.Entity
     */
    public Powerup(double x, double y, double width, double height, Point2D movement, PowerupType type) {
        this.type = type;
        this.movement = movement;
        Rectangle rect = new Rectangle(width, height);
        rect.setArcWidth(20);
        rect.setArcHeight(20);
        rect.setTranslateX(x);
        rect.setTranslateY(y);
        shape = rect;
    }

    public Point2D getMovement() {
        return movement;
    }

    public double getWidth() {
        Rectangle rect = (Rectangle) shape;
        return rect.getWidth();
    }

    public double getHeight() {
        Rectangle rect = (Rectangle) shape;
        return rect.getHeight();
    }

    public PowerupType getType() {
        return type;
    }

    /**
     * Moves the powerup.
     *
     * @param dt amount of time passed since last update in seconds
     */
    public void move(double dt) {
        shape.setTranslateX(getX() + movement.getX() * dt);
        shape.setTranslateY(getY() + movement.getY() * dt);
    }

    /**
     * Checks if powerup is inside game field.
     *
     * @return true if powerup is inbounds, otherwise false
     */
    public boolean inPlay() {
        return shape.getTranslateY() <= GAME_HEIGHT;
    }

}
