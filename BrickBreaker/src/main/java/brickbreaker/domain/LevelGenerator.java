package brickbreaker.domain;

import java.util.ArrayList;
import java.util.List;

public class LevelGenerator {

    private int breakableBricks;

    public LevelGenerator() {
        breakableBricks = 0;
    }
    
    public List<Brick> generate(int level) {
        breakableBricks = 0;
        // The same level every time for now
        List<Brick> bricks = new ArrayList<>();

        int brickWidth = 105;
        int brickHeight = 30;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 12; j++) {
                bricks.add(new Brick(
                        10 + j * brickWidth,
                        70 + i * brickHeight,
                        brickWidth,
                        brickHeight,
                        5 - i,
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
}
