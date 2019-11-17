package brickbreaker.domain;

import java.util.ArrayList;
import java.util.List;

public class LevelGenerator {

    public List<Brick> generate(int level) {
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
                        5 - i
                ));
            }
        }
        
        return bricks;
    }
}
