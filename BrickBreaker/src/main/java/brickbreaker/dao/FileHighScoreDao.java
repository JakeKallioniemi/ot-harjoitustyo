package brickbreaker.dao;

import brickbreaker.domain.highscore.HighScoreEntry;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHighScoreDao implements HighScoreDao {

    private String fileName;

    public FileHighScoreDao(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Reads and lists all the high score entries from file.
     *
     * @return list of the high score entries
     * @throws IOException
     */
    @Override
    public List<HighScoreEntry> list() throws IOException {
        List<HighScoreEntry> scores = new ArrayList<>();
        Scanner scanner = new Scanner(new File(fileName));
        scanner.useDelimiter("\n");

        while (scanner.hasNext()) {
            String[] fields = scanner.next().split("\t");
            String name = fields[0];
            int score = Integer.parseInt(fields[1].trim());
            HighScoreEntry entry = new HighScoreEntry(name, score);
            scores.add(entry);
        }
        scanner.close();

        return scores;
    }

    /**
     * Writes the supplied scores to file, overwriting the old ones.
     *
     * @param scores list of scores to be written
     * @throws IOException
     */
    @Override
    public void add(List<HighScoreEntry> scores) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
        scores.forEach(entry -> {
            printWriter.println(entry.getName() + "\t" + entry.getScore());
        });
        printWriter.close();
    }

}
