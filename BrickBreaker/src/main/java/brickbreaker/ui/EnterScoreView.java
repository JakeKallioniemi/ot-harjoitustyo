package brickbreaker.ui;

import static brickbreaker.BrickBreaker.GAME_HEIGHT;
import static brickbreaker.BrickBreaker.GAME_WIDTH;
import brickbreaker.domain.highscore.HighScoreService;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class EnterScoreView implements View {

    private ViewManager viewManager;
    private HighScoreService highScoreService;
    private Scene scene;
    private char[] chars;
    private int column;
    private int[] selected;
    private List<Text> charTexts;
    private int score;

    public EnterScoreView(ViewManager viewManager, HighScoreService highScoreService) {
        this.viewManager = viewManager;
        this.highScoreService = highScoreService;
        chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void enter(List<Object> args) {
        charTexts = new ArrayList<>();
        score = (int) args.get(0);
        selected = new int[3];
        column = 0;
        scene = createScene();
    }

    private Scene createScene() {
        Text title = new Text("NEW HIGHSCORE!");
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(new Font(140));
        title.setFill(Color.WHITE);

        Text text = new Text("NAME");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(new Font(100));
        text.setFill(Color.WHITE);

        HBox box = createCharactersBox();

        StackPane root = new StackPane(title, box);
        StackPane.setAlignment(title, Pos.TOP_CENTER);

        Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT, Color.BLACK);

        scene.setOnKeyPressed(event -> {
            handleKeyPress(event);
        });

        update();

        return scene;
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                selected[column]--;
                if (selected[column] < 0) {
                    selected[column] = chars.length - 1;
                }
                break;
            case DOWN:
                selected[column]++;
                if (selected[column] >= chars.length) {
                    selected[column] = 0;
                }
                break;
            case LEFT:
                column--;
                if (column < 0) {
                    column = selected.length - 1;
                }
                break;
            case RIGHT:
                column++;
                if (column >= selected.length) {
                    column = 0;
                }
                break;
            case ENTER:
                select();
                break;
        }
        update();
    }

    private void select() {
        String name = "";
        for (int i = 0; i < selected.length; i++) {
            name += chars[selected[i]];
        }
        highScoreService.addScore(name, score);
        viewManager.changeView("SCORES", null);
    }

    private HBox createCharactersBox() {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.setPadding(new Insets(10));
        for (int i = 0; i < 3; i++) {
            Text c = createCharacter();
            charTexts.add(c);
            box.getChildren().add(c);
        }
        return box;
    }

    private Text createCharacter() {
        Text text = new Text();
        text.setFont(new Font(150));
        text.setFill(Color.WHITE);
        return text;
    }

    private void update() {
        for (int i = 0; i < charTexts.size(); i++) {
            Text text = charTexts.get(i);
            text.setText(String.valueOf(chars[selected[i]]));
        }
        charTexts.forEach(i -> i.setFill(Color.WHITE));
        charTexts.get(column).setFill(Color.AQUA);
    }
}
