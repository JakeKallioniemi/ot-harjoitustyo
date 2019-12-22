package brickbreaker.domain.highscore;

public class HighScoreEntry implements Comparable<HighScoreEntry> {

    private String name;
    private int score;

    public HighScoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(HighScoreEntry o) {
        return o.score - this.score;
    }
}
