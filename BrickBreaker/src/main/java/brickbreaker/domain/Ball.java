package brickbreaker.domain;

import static brickbreaker.BrickBreaker.GAME_HEIGHT;
import static brickbreaker.BrickBreaker.GAME_WIDTH;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Ball implements Entity {

    private Circle shape;
    private Point2D movement;

    public Ball(double radius) {
        shape = new Circle(radius);
        shape.setTranslateX(GAME_WIDTH / 2);
        shape.setTranslateY(GAME_HEIGHT - 40 - radius);
        movement = Point2D.ZERO;
    }

    public void move() {
        shape.setTranslateX(shape.getTranslateX() + movement.getX());
        shape.setTranslateY(shape.getTranslateY() + movement.getY());

        if (shape.getTranslateX() - shape.getRadius() < 0
                || shape.getTranslateX() + shape.getRadius() > GAME_WIDTH) {
            movement = new Point2D(-movement.getX(), movement.getY());
        }

        if (shape.getTranslateY() - shape.getRadius() < 0) {
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
        return shape.getTranslateY() - shape.getRadius() > GAME_HEIGHT;
    }
    
    public Point2D getMovement() {
        return movement;
    }

    public void setMovement(Point2D movement) {
        this.movement = movement;
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    @Override
    public boolean intersects(Entity other) {
        Shape intersection = Shape.intersect(this.getShape(), other.getShape());
        return intersection.getBoundsInLocal().getWidth() != -1;
    }
}
