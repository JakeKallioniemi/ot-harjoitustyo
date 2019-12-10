package brickbreaker.domain;

import static brickbreaker.BrickBreaker.GAME_HEIGHT;
import static brickbreaker.BrickBreaker.GAME_WIDTH;
import javafx.scene.shape.Rectangle;

public class Paddle extends Entity {

    public Paddle(double width, double height) {
        Rectangle rect = new Rectangle(width, height);
        rect.setArcWidth(20);
        rect.setArcHeight(20);
        rect.setTranslateX(GAME_WIDTH / 2 - width / 2);
        rect.setTranslateY(GAME_HEIGHT - height - 10);
        shape = rect;
    }

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

    public void resetPosition() {
        shape.setTranslateX(GAME_WIDTH / 2 - getWidth() / 2);
        shape.setTranslateY(GAME_HEIGHT - getHeight() - 10);
    }

}
