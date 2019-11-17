package brickbreaker.domain;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Brick implements Entity {

    private int type;
    private Rectangle shape;

    public Brick(double x, double y, double width, double height, int type) {
        shape = new Rectangle(width, height);
        shape.setTranslateX(x);
        shape.setTranslateY(y);
        this.type = type;
    }

    public int getType() {
        return type;
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
