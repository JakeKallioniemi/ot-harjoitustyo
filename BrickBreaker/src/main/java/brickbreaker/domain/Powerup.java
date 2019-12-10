package brickbreaker.domain;

import static brickbreaker.BrickBreaker.GAME_HEIGHT;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class Powerup extends Entity {

    private PowerupType type;
    private Point2D movement;

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

    public void move(double dt) {
        shape.setTranslateX(getX() + movement.getX() * dt);
        shape.setTranslateY(getY() + movement.getY() * dt);
    }

    public boolean inPlay() {
        return shape.getTranslateY() <= GAME_HEIGHT;
    }

}
