package brickbreaker.domain;

import javafx.scene.shape.Shape;

public abstract class Entity {

    protected Shape shape;
    
    public double getX() {
        return shape.getTranslateX();
    }
    public double getY() {
        return shape.getTranslateY();
    }  
    
    public Shape getShape() {
        return shape;
    }
    
    public boolean intersects(Entity other) {
        Shape intersection = Shape.intersect(this.getShape(), other.getShape());
        return intersection.getBoundsInLocal().getWidth() != -1;
    }
    
}
