package brickbreaker.domain;

import javafx.scene.shape.Shape;

// maybe use inheritance instead?
public interface Entity {

    public double getX();
    public double getY();  
    public Shape getShape();
    public boolean intersects(Entity other);
}
