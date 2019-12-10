package brickbreaker.domain;

import javafx.scene.shape.Rectangle;

public class Brick extends Entity {

    private int type;
    private boolean breakable;
    private int hitsLeft;

    /**
     * Creates a new Brick that extends Entity. The shape of the Brick is a
     * Rectangle object.
     *
     * @param x the x-coordinate of the brick
     * @param y the y-coordinate of the brick
     * @param width the width of the brick
     * @param height the height of the brick
     * @param type the type of the brick
     * @param breakable mark brick as breakable or not
     * @param hitsLeft amount of hits it takes to break the brick
     * @see brickbreaker.domain.Entity
     */
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

    /**
     * Reduce the health of the brick by one.
     */
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
