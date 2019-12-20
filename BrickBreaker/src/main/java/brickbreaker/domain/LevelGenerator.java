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
    private final int normalTypeCount;

    /**
     * Creates a new LevelGenerator that is used to randomly generate levels
     * from different row types.
     *
     * @param columns amount of bricks per column
     * @param rows amount of rows
     * @param brickWidth width of a brick
     * @param brickHeight height of a brick
     * @param random used for level generation
     */
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
        normalTypeCount = 7;
    }

    /**
     * Randomly generates a new arrangement of bricks using the Random object
     * supplied in constructor.
     *
     * @return list of generated bricks
     */
    public List<Brick> generate() {
        List<Brick> bricks = new ArrayList<>();
        breakableBricks = 0;
        for (int i = 0; i < rows; i++) {
            generateRow(bricks, i);
        }
        return bricks;
    }

    public int breakableBrickCount() {
        return breakableBricks;
    }

    private void generateRow(List<Brick> bricks, int row) {
        // The range is higher than the amount of cases
        // to increase the chance of rolling a normal row.
        int roll = random.nextInt(8);

        switch (roll) {
            case 0:
                // Empty row.
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

    // The row methods could be combined by using flags
    // but I think it's more clear this way.
    private void gapsRow(List<Brick> bricks, int row) {
        int type = random.nextInt(normalTypeCount);
        for (int j = 0; j < columns; j += 2) {
            bricks.add(createBrick(j, row, type));
        }
    }

    private void alternatingRow(List<Brick> bricks, int row) {
        // Becomes a normal row if same type rolled twice.
        int firstType = random.nextInt(normalTypeCount);
        int secondType = random.nextInt(normalTypeCount);
        for (int j = 0; j < columns; j++) {
            if (j % 2 == 0) {
                bricks.add(createBrick(j, row, firstType));
            } else {
                bricks.add(createBrick(j, row, secondType));
            }
        }
    }

    private void unbreakableRow(List<Brick> bricks, int row) {
        for (int j = 0; j < columns; j += 3) {
            bricks.add(createBrick(j, row, 8));
        }
    }

    private void multihitRow(List<Brick> bricks, int row) {
        for (int j = 0; j < columns; j++) {
            bricks.add(createBrick(j, row, 7));
        }
    }

    private void normalRow(List<Brick> bricks, int row) {
        int type = random.nextInt(normalTypeCount);
        for (int j = 0; j < columns; j++) {
            bricks.add(createBrick(j, row, type));
        }
    }

    private Brick createBrick(int column, int row, int type) {
        int hitsToDestroy = type == 7 ? 3 : 1;
        boolean breakable = type != 8;
        Brick brick = new Brick(
                sideSpace + column * brickWidth, topSpace + row * brickHeight,
                brickWidth, brickHeight,
                type, breakable, hitsToDestroy
        );
        if (breakable) {
            breakableBricks++;
        }
        return brick;
    }
}
