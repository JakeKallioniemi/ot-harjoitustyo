package brickbreaker.domain;

import javafx.scene.shape.Shape;

public interface Entity {

    public Shape getShape();
    public boolean intersects(Entity other);
}
