package brickbreaker.ui;

import static brickbreaker.BrickBreaker.GAME_HEIGHT;
import static brickbreaker.BrickBreaker.GAME_WIDTH;
import brickbreaker.domain.highscore.HighScoreEntry;
import brickbreaker.domain.highscore.HighScoreService;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ScoresView implements View {

    private ViewManager viewManager;
    private HighScoreService highScoreService;
    private Scene scene;

    public ScoresView(ViewManager viewManager, HighScoreService highScoreService) {
        this.viewManager = viewManager;
        this.highScoreService = highScoreService;
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void enter(List<Object> args) {
        scene = createScene();
    }

    private Scene createScene() {
        Text title = new Text("HIGHSCORES");
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(new Font(100));
        title.setFill(Color.WHITE);

        VBox scoreBox = createScoreBox();

        StackPane root = new StackPane(title, scoreBox);
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setMargin(title, new Insets(10));

        Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT, Color.BLACK);

        scene.setOnKeyPressed(event -> {
            viewManager.changeView("MENU", null);
        });

        return scene;
    }

    private VBox createScoreBox() {
        VBox box = new VBox();
        box.setAlignment(Pos.BOTTOM_CENTER);
        box.setPadding(new Insets(20, 0, 50, 0));

        List<HighScoreEntry> scores = highScoreService.getScores();
        int highlightIndex = highScoreService.getNewestScoreIndex();
        for (int i = 0; i < scores.size(); i++) {
            HighScoreEntry score = scores.get(i);
            Text text = new Text((i + 1) + ".  " + score.getName() + "  " + score.getScore());
            text.setFont(new Font(40));
            if (i == highlightIndex) {
                text.setFill(Color.GOLD);
            } else {
                text.setFill(Color.WHITE);
            }
            text.setTextAlignment(TextAlignment.LEFT);
            box.getChildren().add(text);
        }
        return box;
    }
}
