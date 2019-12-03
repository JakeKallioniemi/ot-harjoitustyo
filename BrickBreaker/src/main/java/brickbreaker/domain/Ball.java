package brickbreaker.domain;

import static brickbreaker.BrickBreaker.GAME_HEIGHT;
import static brickbreaker.BrickBreaker.GAME_WIDTH;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

public class Ball extends Entity {

    private Point2D movement;

    public Ball(double radius) {
        shape = new Circle(radius);
        shape.setTranslateX(GAME_WIDTH / 2);
        shape.setTranslateY(GAME_HEIGHT - 40 - radius);
        movement = Point2D.ZERO;
    }

    public void move(double dt) {
        shape.setTranslateX(getX() + movement.getX() * dt);
        shape.setTranslateY(getY() + movement.getY() * dt);

        if (getX() - getRadius() < 0
                || getX() + getRadius() > GAME_WIDTH) {
            movement = new Point2D(-movement.getX(), movement.getY());
        }

        if (getY() - getRadius() < 0) {
            movement = new Point2D(movement.getX(), -movement.getY());
        }
    }

    public void stop() {
        movement = Point2D.ZERO;
    }

    public void setPosition(double x, double y) {
        shape.setTranslateX(x);
        shape.setTranslateY(y);
    }

    public boolean inPlay() {
        return shape.getTranslateY() - getRadius() < GAME_HEIGHT;
    }

    public Point2D getMovement() {
        return movement;
    }

    public void setMovement(Point2D movement) {
        this.movement = movement;
    }

    public double getRadius() {
        Circle circ = (Circle) shape;
        return circ.getRadius();
    }
    
}
