package brickbreaker.domain;

import static brickbreaker.BrickBreaker.GAME_HEIGHT;
import static brickbreaker.BrickBreaker.GAME_WIDTH;
import javafx.scene.shape.Rectangle;

public class Paddle extends Entity {

    public Paddle(double width, double height) {
        shape = new Rectangle(width, height);
        shape.setTranslateX(GAME_WIDTH / 2 - width / 2);
        shape.setTranslateY(GAME_HEIGHT - height - 10);
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

    public void reset() {
        shape.setTranslateX(GAME_WIDTH / 2 - getWidth() / 2);
        shape.setTranslateY(GAME_HEIGHT - getHeight() - 10);
    }
    
}
