package brickbreaker.ui;

import static brickbreaker.BrickBreaker.GAME_HEIGHT;
import static brickbreaker.BrickBreaker.GAME_WIDTH;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ScoresView implements View {

    // TODO
    private ViewManager viewManager;
    private Scene scene;

    public ScoresView(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void enter() {
        scene = createScene();
    }

    private Scene createScene() {
        Text text = new Text("TODO");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(new Font(200));
        text.setFill(Color.WHITE);

        StackPane root = new StackPane(text);
        StackPane.setAlignment(text, Pos.TOP_CENTER);
        StackPane.setMargin(text, new Insets(70));

        Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT, Color.BLACK);

        scene.setOnKeyPressed(event -> {
            viewManager.changeView("MENU");
        });
        
        return scene;
    }
}
