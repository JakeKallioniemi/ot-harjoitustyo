/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brickbreaker.domain;

import brickbreaker.domain.mocks.MockRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LevelGeneratorTest {

    private List<Integer> values;
    private LevelGenerator generator;

    @Before
    public void setUp() {
        values = new ArrayList<>();
        Random random = new MockRandom(values);
        generator = new LevelGenerator(4, 1, 98, 30, random);
    }

    @Test
    public void rowIsGenerated() {
        values.addAll(Arrays.asList(6, 1));
        List<Brick> bricks = generator.generate();
        assertEquals(4, bricks.size());
    }

    @Test
    public void brickSizeCorrect() {
        values.addAll(Arrays.asList(6, 1));
        Brick brick = generator.generate().get(0);
        assertEquals(98, brick.getWidth(), 0.01);
        assertEquals(30, brick.getHeight(), 0.01);
    }

    @Test
    public void breakableBrickCountValueCorrect() {
        values.addAll(Arrays.asList(5, 1));
        generator.generate();
        assertEquals(4, generator.getBreakableBrickCount());
    }

    @Test
    public void normalRowBrickTypesSame() {
        values.addAll(Arrays.asList(5, 1));
        List<Brick> bricks = generator.generate();
        assertEquals(1, bricks.get(0).getType());
        assertEquals(1, bricks.get(1).getType());
        assertEquals(1, bricks.get(2).getType());
        assertEquals(1, bricks.get(3).getType());
    }

    @Test
    public void emptyRowIsEmpty() {
        values.add(0);
        List<Brick> bricks = generator.generate();
        assertEquals(0, bricks.size());
    }

    @Test
    public void breakableBrickCountCorrectWhenUnbreakableRow() {
        values.add(3);
        generator.generate();
        assertEquals(0, generator.getBreakableBrickCount());
    }

    @Test
    public void alternatingRowAlternates() {
        values.addAll(Arrays.asList(2, 3, 4));
        List<Brick> bricks = generator.generate();
        assertEquals(3, bricks.get(0).getType());
        assertEquals(4, bricks.get(1).getType());
        assertEquals(3, bricks.get(2).getType());
        assertEquals(4, bricks.get(3).getType());
    }

    @Test
    public void gapRowHasGaps() {
        values.addAll(Arrays.asList(1, 1));
        List<Brick> bricks = generator.generate();
        assertEquals(2, bricks.size());
    }
}
