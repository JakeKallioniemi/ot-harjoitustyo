package brickbreaker.domain;

import javafx.scene.shape.Rectangle;

public class Brick extends Entity {

    private int type;
    private boolean breakable;
    private int hitsLeft;

    public Brick(double x, double y, double width, double height, int type,
            boolean breakable, int hitsLeft) {
        
        shape = new Rectangle(width, height);
        shape.setTranslateX(x);
        shape.setTranslateY(y);
        this.type = type;
        this.breakable = breakable;
        this.hitsLeft = hitsLeft;
    }

    public int getType() {
        return type;
    }
    
    public boolean isBreakable() {
        return breakable;
    }
    
    public int hitsLeft() {
        return hitsLeft;
    }
    
    public void hit() {
        hitsLeft--;
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
