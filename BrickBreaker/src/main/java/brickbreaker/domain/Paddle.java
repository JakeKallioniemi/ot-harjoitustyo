package brickbreaker.domain;

import static brickbreaker.BrickBreaker.GAME_HEIGHT;
import static brickbreaker.BrickBreaker.GAME_WIDTH;
import javafx.scene.shape.Rectangle;

public class Paddle extends Entity {

    /**
     * Creates a new Paddle that extends Entity. Starting position is the bottom
     * center of the screen. The shape of the Paddle is a Rectangle object.
     *
     * @param width the width of the paddle
     * @param height the height of the paddle
     * @see brickbreaker.domain.Entity
     */
    public Paddle(double width, double height) {
        Rectangle rect = new Rectangle(width, height);
        rect.setArcWidth(20);
        rect.setArcHeight(20);
        rect.setTranslateX(GAME_WIDTH / 2 - width / 2);
        rect.setTranslateY(GAME_HEIGHT - height - 10);
        shape = rect;
    }

    /**
     * Moves the paddle along the x-axis and keeps it within game field.
     *
     * @param dx the direction to move to, left if negative, right if positive
     * @param dt amount of time passed since last update in seconds
     */
    public void move(double dx, double dt) {
        shape.setTranslateX(shape.getTranslateX() + dx * dt);

        if (shape.getTranslateX() < 0) {
            shape.setTranslateX(0);
        } else if (shape.getTranslateX() + getWidth() > GAME_WIDTH) {
            shape.setTranslateX(GAME_WIDTH - getWidth());
        }
    }

    public double getWidth() {
        Rectangle rect = (Rectangle) shape;
        return rect.getWidth();
    }

    public double getHeight() {
        Rectangle rect = (Rectangle) shape;
        return rect.getHeight();
    }

    public void setWidth(double width) {
        Rectangle rect = (Rectangle) shape;
        rect.setWidth(width);
    }

    /**
     * Resets the position of the paddle to bottom middle of the game field.
     */
    public void resetPosition() {
        shape.setTranslateX(GAME_WIDTH / 2 - getWidth() / 2);
        shape.setTranslateY(GAME_HEIGHT - getHeight() - 10);
    }

}
