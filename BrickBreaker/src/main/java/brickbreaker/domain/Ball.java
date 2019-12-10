package brickbreaker.domain;

import static brickbreaker.BrickBreaker.GAME_HEIGHT;
import static brickbreaker.BrickBreaker.GAME_WIDTH;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

public class Ball extends Entity {

    private Point2D movement;
    private boolean unstoppable;

    /**
     * Creates a new Ball that extends Entity. The ball is stationary in the
     * beginning. Starting position is just above the paddle. The shape of the
     * Ball is a Circle object.
     *
     * @param radius the radius of the ball
     * @param unstoppable tells if ball can be stopped by bricks or not
     * @see brickbreaker.domain.Entity
     */
    public Ball(double radius, boolean unstoppable) {
        this.unstoppable = unstoppable;
        shape = new Circle(radius);
        shape.setTranslateX(GAME_WIDTH / 2);
        shape.setTranslateY(GAME_HEIGHT - 40 - radius);
        movement = Point2D.ZERO;
    }

    /**
     * Moves the ball and bounces it from walls if necessary.
     *
     * @param dt amount of time passed since last update in seconds
     */
    public void move(double dt) {
        shape.setTranslateX(getX() + movement.getX() * dt);
        shape.setTranslateY(getY() + movement.getY() * dt);

        if (getX() - getRadius() < 0) {
            movement = new Point2D(-movement.getX(), movement.getY());
            shape.setTranslateX(getRadius());
        } else if (getX() + getRadius() > GAME_WIDTH) {
            movement = new Point2D(-movement.getX(), movement.getY());
            shape.setTranslateX(GAME_WIDTH - getRadius());
        }
        if (getY() - getRadius() < 0) {
            movement = new Point2D(movement.getX(), -movement.getY());
            shape.setTranslateY(getRadius());
        }
    }

    /**
     * Stops the ball from moving.
     */
    public void stop() {
        movement = Point2D.ZERO;
    }

    public boolean isUnstoppable() {
        return unstoppable;
    }

    public void setUnstoppable(boolean unstoppable) {
        this.unstoppable = unstoppable;
    }

    /**
     * Set the x and y coordinates of ball.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public void setPosition(double x, double y) {
        shape.setTranslateX(x);
        shape.setTranslateY(y);
    }

    /**
     * Checks if ball is inside game field.
     *
     * @return true if ball is inbounds, otherwise false
     */
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

    public void setRadius(double radius) {
        Circle circ = (Circle) shape;
        circ.setRadius(radius);
    }
}
