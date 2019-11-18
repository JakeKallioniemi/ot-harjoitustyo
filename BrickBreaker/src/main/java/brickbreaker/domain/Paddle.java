package brickbreaker.domain;

import static brickbreaker.BrickBreaker.GAME_HEIGHT;
import static brickbreaker.BrickBreaker.GAME_WIDTH;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Paddle implements Entity {

    private Rectangle shape;

    public Paddle(double width, double height) {
        shape = new Rectangle(width, height);
        shape.setTranslateX(GAME_WIDTH / 2 - width / 2);
        shape.setTranslateY(GAME_HEIGHT - height - 10);
    }

    public void move(double dx, double dt) {
        shape.setTranslateX(shape.getTranslateX() + dx * dt);

        if (shape.getTranslateX() < 0) {
            shape.setTranslateX(0);
        } else if (shape.getTranslateX() + shape.getWidth() > GAME_WIDTH) {
            shape.setTranslateX(GAME_WIDTH - shape.getWidth());
        }
    }

    public double getWidth() {
        return shape.getWidth();
    }

    public double getHeight() {
        return shape.getHeight();
    }

    @Override
    public double getX() {
        return shape.getTranslateX();
    }

    @Override
    public double getY() {
        return shape.getTranslateY();
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
