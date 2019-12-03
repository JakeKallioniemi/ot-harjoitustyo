package brickbreaker.domain;

import javafx.scene.shape.Rectangle;

public class Brick extends Entity {

    private int type;

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
        Rectangle rect = (Rectangle) shape;
        return rect.getWidth();
    }
    
    public double getHeight() {
        Rectangle rect = (Rectangle) shape;
        return rect.getHeight();
    }

}
