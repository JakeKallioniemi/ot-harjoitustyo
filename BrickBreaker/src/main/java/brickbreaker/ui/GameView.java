package brickbreaker.ui;

import static brickbreaker.BrickBreaker.GAME_HEIGHT;
import static brickbreaker.BrickBreaker.GAME_WIDTH;
import brickbreaker.domain.Brick;
import brickbreaker.domain.Game;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GameView implements View {

    private ViewManager viewManager;
    private Game game;
    private AnimationTimer gameLoop;
    private Scene scene;
    private boolean paused;

    public GameView(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void enter(List<Object> args) {
        game = new Game();
        game.newLevel();
        scene = createScene();
        paused = true;
        gameLoop.start();
    }

    private Scene createScene() {
        StringProperty score = new SimpleStringProperty();
        StringProperty lives = new SimpleStringProperty();

        score.setValue("Score: " + game.getScore());
        lives.setValue("Lives: " + game.getLives());

        Group root = new Group();
        addNodes(root, score, lives);

        Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT, Color.DARKGRAY);

        Set<KeyCode> keysPressed = new HashSet<>();
        scene.setOnKeyPressed(event -> {
            keysPressed.add(event.getCode());
        });
        scene.setOnKeyReleased(event -> {
            keysPressed.remove(event.getCode());
        });

        gameLoop = createGameLoop(keysPressed, root, score, lives);

        return scene;
    }

    private AnimationTimer createGameLoop(Set<KeyCode> keysPressed, Group root,
            StringProperty score, StringProperty lives) {

        AnimationTimer timer = new AnimationTimer() {

            long prevTime = System.nanoTime();

            @Override
            public void handle(long currentTime) {
                double dt = (currentTime - prevTime) / 1000000000.0;
                prevTime = currentTime;

                if (keysPressed.contains(KeyCode.ESCAPE)) {
                    gameLoop.stop();
                    viewManager.changeView("MENU", null);
                }
                if (keysPressed.contains(KeyCode.ENTER) && paused) {
                    paused = false;
                    game.start();
                    int size = root.getChildren().size();
                    root.getChildren().remove(size - 1);
                }
                if (keysPressed.contains(KeyCode.LEFT)) {
                    game.movePaddle(-600, dt);
                }
                if (keysPressed.contains(KeyCode.RIGHT)) {
                    game.movePaddle(600, dt);
                }
                if (paused) {
                    game.resetBall();
                    return;
                }

                game.moveBall(dt);
                game.handlePaddleCollision();

                List<Brick> toBeRemoved = game.handleBrickCollision();
                if (toBeRemoved.size() > 0) {
                    toBeRemoved.forEach(brick -> {
                        root.getChildren().remove(brick.getShape());
                    });
                }
                score.setValue("Score: " + game.getScore());

                if (!game.ballInPlay()) {
                    game.resetBall();
                    game.resetPaddle();
                    root.getChildren().add(createStartBox());
                    paused = true;
                }
                lives.setValue("Lives: " + game.getLives());

                if (game.isOver()) {
                    gameLoop.stop();
                    List<Object> args = new ArrayList<>();
                    args.add(game.getScore());
                    viewManager.changeView("GAME_OVER", args);
                }

                if (game.levelCleared()) {
                    game.newLevel();
                    addNodes(root, score, lives);
                    paused = true;
                }
            }
        };
        return timer;
    }

    private void addNodes(Group root, StringProperty score, StringProperty lives) {
        root.getChildren().clear();

        root.getChildren().add(createInfoBox(score, lives));
        root.getChildren().add(game.getPaddle().getShape());
        root.getChildren().add(game.getBall().getShape());

        Color[] brickColors = new Color[]{
            Color.BLUE,
            Color.RED,
            Color.GREEN,
            Color.ORANGE,
            Color.YELLOW,
            Color.VIOLET
        };

        game.getBricks().forEach(brick -> {
            Shape shape = brick.getShape();
            shape.setFill(brickColors[brick.getType()]);
            shape.setStroke(Color.BLACK);
            root.getChildren().add(shape);
        });

        root.getChildren().add(createStartBox());
    }

    private VBox createInfoBox(StringProperty score, StringProperty lives) {
        Text scoreCounter = createInfoText(score);
        Text livesCounter = createInfoText(lives);

        VBox info = new VBox(scoreCounter, livesCounter);
        info.setTranslateX(GAME_WIDTH - 100);
        info.setTranslateY(10);

        return info;
    }
    
    private Text createInfoText(StringProperty property) {
        Text text = new Text();
        text.setFont(new Font(15));
        text.textProperty().bind(property);
        return text;
    }

    private VBox createStartBox() {
        Text level = createText("LEVEL " + game.getLevel(), 200);
        Text instruction = createText("PRESS ENTER TO START", 100);

        VBox box = new VBox(level, instruction);
        box.setTranslateX(150);
        box.setTranslateY(50);

        return box;
    }

    private Text createText(String value, int fontSize) {
        Text text = new Text(value);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(new Font(fontSize));
        text.setFill(Color.BLACK);
        text.setStroke(Color.DARKGRAY);
        return text;
    }
}
