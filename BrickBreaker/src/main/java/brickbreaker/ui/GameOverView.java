package brickbreaker.ui;

import static brickbreaker.BrickBreaker.GAME_HEIGHT;
import static brickbreaker.BrickBreaker.GAME_WIDTH;
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

public class GameOverView implements View {

    private ViewManager viewManager;
    private Scene scene;
    private int score;

    public GameOverView(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public void enter(List<Object> args) {
        score = (int) args.get(0);
        scene = createScene();
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    private Scene createScene() {
        Text title = createText("GAME OVER", 150);
        Text scoreLabelText = createText("SCORE", 100);
        Text scoreText = createText(String.valueOf(score), 150);

        VBox box = new VBox(title, scoreLabelText, scoreText);
        box.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(title, box);
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setMargin(title, new Insets(20));

        Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT, Color.BLACK);

        scene.setOnKeyPressed(event -> {
            viewManager.changeView("MENU", null);
        });

        return scene;
    }

    private Text createText(String value, int fontSize) {
        Text text = new Text(value);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(new Font(fontSize));
        text.setFill(Color.WHITE);
        return text;
    }
}
