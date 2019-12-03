package brickbreaker;

import brickbreaker.dao.FileHighScoreDao;
import brickbreaker.dao.HighScoreDao;
import brickbreaker.domain.HighScoreService;
import brickbreaker.ui.EnterScoreView;
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
        HighScoreDao highScoreDao = new FileHighScoreDao("scores.txt");
        HighScoreService highScoreService = new HighScoreService(highScoreDao, 10);
       
        viewManager = new ViewManager();      
        viewManager.addView("MENU", new MenuView(viewManager));
        viewManager.addView("PLAY", new GameView(viewManager));
        viewManager.addView("SCORES", new ScoresView(viewManager, highScoreService));
        viewManager.addView("GAME_OVER", new GameOverView(viewManager, highScoreService));
        viewManager.addView("ENTER_SCORE", new EnterScoreView(viewManager, highScoreService));
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
