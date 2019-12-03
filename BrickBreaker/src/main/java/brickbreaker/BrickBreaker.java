package brickbreaker;

import brickbreaker.ui.GameOverView;
import brickbreaker.ui.GameView;
import brickbreaker.ui.MenuView;
import brickbreaker.ui.ScoresView;
import brickbreaker.ui.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class BrickBreaker extends Application {

    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;

    private ViewManager viewManager;

    @Override
    public void init() {
        viewManager = new ViewManager();
        
        viewManager.addView("MENU", new MenuView(viewManager));
        viewManager.addView("PLAY", new GameView(viewManager));
        viewManager.addView("SCORES", new ScoresView(viewManager));
        viewManager.addView("GAME_OVER", new GameOverView(viewManager));
    }

    @Override
    public void start(Stage stage) {
        viewManager.setStage(stage);
        viewManager.changeView("MENU", null);
        stage.setTitle("BrickBreaker");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
