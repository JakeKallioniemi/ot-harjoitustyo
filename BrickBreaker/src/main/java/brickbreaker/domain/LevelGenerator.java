package brickbreaker.domain;

import static brickbreaker.BrickBreaker.GAME_WIDTH;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelGenerator {

    private Random random;
    private int brickWidth;
    private int brickHeight;
    private int columns;
    private int rows;
    private int breakableBricks;
    private int topSpace;
    private int sideSpace;

    public LevelGenerator(int columns, int rows, int brickWidth,
            int brickHeight, Random random) {

        this.random = random;
        this.columns = columns;
        this.rows = rows;
        this.brickWidth = brickWidth;
        this.brickHeight = brickHeight;
        breakableBricks = 0;
        topSpace = 70;
        sideSpace = (GAME_WIDTH - columns * brickWidth) / 2;
    }

    public List<Brick> generate(int level) {
        breakableBricks = 0;
        List<Brick> bricks = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 13; j++) {
                bricks.add(new Brick(
                        3 + j * brickWidth,
                        70 + i * brickHeight,
                        brickWidth,
                        brickHeight,
                        6 - i,
                        true,
                        1
                ));
                breakableBricks++;
            }
        }

        return bricks;
    }

    public int breakableBrickCount() {
        return breakableBricks;
    }

    private void generateRow(List<Brick> bricks, int row) {
        int roll = random.nextInt(8);

        switch (roll) {
            case 0:
                // Empty row
                break;
            case 1:
                gapsRow(bricks, row);
                break;
            case 2:
                alternatingRow(bricks, row);
                break;
            case 3:
                unbreakableRow(bricks, row);
                break;
            case 4:
                multihitRow(bricks, row);
                break;
            default:
                normalRow(bricks, row);
                break;
        }
    }

    private void gapsRow(List<Brick> bricks, int row) {
        int type = random.nextInt(7);
        for (int j = 0; j < columns; j += 2) {
            bricks.add(createBrick(j, row, type));
        }
    }

    private void alternatingRow(List<Brick> bricks, int row) {
        int firstType = random.nextInt(7);
        int secondType = random.nextInt(7);
        for (int j = 0; j < columns; j++) {
            
        }
    }

    private void unbreakableRow(List<Brick> bricks, int row) {
    }

    private void multihitRow(List<Brick> bricks, int row) {
    }

    private void normalRow(List<Brick> bricks, int row) {
    }
    
    private Brick createBrick(int column, int row, int type) {
        int hitsToDestroy = type == 7 ? 3 : 1;
        boolean breakable = type == 8;
        Brick brick = new Brick(
                sideSpace + column * brickWidth, topSpace + row * brickHeight,
                brickWidth, brickHeight,
                type, breakable, hitsToDestroy
        );
        return brick;
    }
}
