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

    /**
     * Check if two entities intersect.
     *
     * @param other Entity to check collision with
     * @return true if the two entities intersect, otherwise false
     */
    public boolean intersects(Entity other) {
        Shape intersection = Shape.intersect(this.getShape(), other.getShape());
        return intersection.getBoundsInLocal().getWidth() != -1;
    }

}
