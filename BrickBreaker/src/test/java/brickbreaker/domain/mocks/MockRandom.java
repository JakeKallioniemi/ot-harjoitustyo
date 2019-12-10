package brickbreaker.domain.mocks;

import java.util.List;
import java.util.Random;

public class MockRandom extends Random {

    private List<Integer> values;
    private int current;
    
    public MockRandom(List<Integer> values) {
        this.values = values;
        current = 0;
    }
    
    @Override
    public int nextInt(int bound) {
        int nextValue = values.get(current);
        current++;
        return nextValue;
    }
}
