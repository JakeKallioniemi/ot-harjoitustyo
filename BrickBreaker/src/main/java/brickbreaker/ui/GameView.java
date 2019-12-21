package brickbreaker.ui;

import static brickbreaker.BrickBreaker.GAME_HEIGHT;
import static brickbreaker.BrickBreaker.GAME_WIDTH;
import brickbreaker.domain.Ball;
import brickbreaker.domain.Brick;
import brickbreaker.domain.Game;
import brickbreaker.domain.LevelGenerator;
import brickbreaker.domain.Powerup;
import brickbreaker.domain.PowerupService;
import brickbreaker.domain.PowerupType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GameView implements View {

    private ViewManager viewManager;
    private Game game;
    private AnimationTimer gameLoop;
    private Scene scene;
    private StringProperty score;
    private StringProperty lives;
    private StringProperty level;
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
        Group root = new Group();
        List<Brick> bricks = createBrickList(root);
        List<Ball> balls = createBallList(root);
        List<Powerup> powerups = createPowerupList(root);
        Random random = new Random();
        PowerupService powerupService = new PowerupService(random, 8);
        LevelGenerator levelGenerator = new LevelGenerator(13, 8, 98, 30, random);
        game = new Game(balls, bricks, powerups, powerupService, levelGenerator);
        score = new SimpleStringProperty();
        lives = new SimpleStringProperty();
        level = new SimpleStringProperty();
        scene = createScene(root, createStartBox(), createInfoBox());
        paused = true;
        gameLoop.start();
    }

    private Scene createScene(Group root, VBox startBox, VBox infoBox) {
        score.setValue("Score: " + game.getScore());
        lives.setValue("Lives: " + game.getLives());
        level.setValue("ROUND " + game.getLevel());

        Shape shape = game.getPaddle().getShape();
        stylePaddle(shape);

        root.getChildren().addAll(shape, infoBox, startBox);

        Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT, Color.LIGHTGRAY);

        Set<KeyCode> keysPressed = new HashSet<>();
        scene.setOnKeyPressed(event -> {
            keysPressed.add(event.getCode());
        });
        scene.setOnKeyReleased(event -> {
            keysPressed.remove(event.getCode());
        });

        gameLoop = createGameLoop(keysPressed, root, startBox);

        return scene;
    }

    private AnimationTimer createGameLoop(Set<KeyCode> keysPressed, Group root,
            VBox startBox) {

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
                    root.getChildren().remove(startBox);
                }
                if (keysPressed.contains(KeyCode.LEFT)) {
                    game.movePaddle(-1, dt);
                }
                if (keysPressed.contains(KeyCode.RIGHT)) {
                    game.movePaddle(1, dt);
                }
                if (paused) {
                    game.centerBallOnPaddle();
                    return;
                }

                game.update(dt);

                if (!game.inPlay()) {
                    game.resetMovableEntities();
                    level.setValue("ROUND " + game.getLevel());
                    root.getChildren().add(startBox);
                    paused = true;
                }

                score.setValue("Score: " + game.getScore());
                lives.setValue("Lives: " + game.getLives());

                if (game.isOver()) {
                    gameLoop.stop();
                    List<Object> args = new ArrayList<>();
                    args.add(game.getScore());
                    viewManager.changeView("GAME_OVER", args);
                }

                if (game.levelCleared()) {
                    game.newLevel();
                    level.setValue("ROUND " + game.getLevel());
                    root.getChildren().add(startBox);
                    paused = true;
                }
            }
        };
        return timer;
    }

    private VBox createInfoBox() {
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
        Text levelText = createText("", 200);
        levelText.textProperty().bind(level);
        Text instruction = createText("PRESS ENTER TO START", 100);

        VBox box = new VBox(levelText, instruction);
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

    private List<Brick> createBrickList(Group root) {
        ObservableList<Brick> bricks = FXCollections.observableArrayList();
        bricks.addListener((ListChangeListener<Brick>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    List<? extends Brick> added = c.getAddedSubList();
                    added.forEach(brick -> {
                        Shape shape = brick.getShape();
                        styleBrick(shape, brick.getType());
                        root.getChildren().add(shape);
                    });
                }
                if (c.wasRemoved()) {
                    List<? extends Brick> removed = c.getRemoved();
                    removed.forEach(brick -> {
                        root.getChildren().remove(brick.getShape());
                    });
                }
            }
        });

        return bricks;
    }

    private void styleBrick(Shape shape, int type) {
        Color[] brickColors = new Color[]{
            Color.WHITESMOKE,
            Color.ORANGE,
            Color.CYAN,
            Color.GREEN,
            Color.RED,
            Color.BLUE,
            Color.VIOLET,
            Color.GOLD,
            Color.GRAY
        };

        shape.setFill(brickColors[type]);
        shape.setStroke(Color.BLACK);
    }

    private List<Ball> createBallList(Group root) {
        ObservableList<Ball> balls = FXCollections.observableArrayList();
        balls.addListener((ListChangeListener<Ball>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    List<? extends Ball> added = c.getAddedSubList();
                    added.forEach(ball -> {
                        Shape shape = ball.getShape();
                        styleBall(shape);
                        root.getChildren().add(shape);
                    });
                }
                if (c.wasRemoved()) {
                    List<? extends Ball> removed = c.getRemoved();
                    removed.forEach(ball -> {
                        root.getChildren().remove(ball.getShape());
                    });
                }
            }
        });

        return balls;
    }

    private void styleBall(Shape shape) {
        shape.setFill(Color.DARKGRAY);
        shape.setStrokeType(StrokeType.INSIDE);
        shape.setStrokeWidth(3);
        shape.setStroke(Color.BLACK);
    }

    private List<Powerup> createPowerupList(Group root) {
        ObservableList<Powerup> powerups = FXCollections.observableArrayList();
        powerups.addListener((ListChangeListener<Powerup>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    List<? extends Powerup> added = c.getAddedSubList();
                    added.forEach(powerup -> {
                        Shape shape = powerup.getShape();
                        stylePowerup(shape, powerup.getType());
                        root.getChildren().add(shape);
                    });
                }
                if (c.wasRemoved()) {
                    List<? extends Powerup> removed = c.getRemoved();
                    removed.forEach(powerup -> {
                        root.getChildren().remove(powerup.getShape());
                    });
                }
            }
        });

        return powerups;
    }

    private void stylePowerup(Shape shape, PowerupType type) {
        Color[] powerupColors = new Color[]{
            Color.AQUA,
            Color.DARKORCHID,
            Color.HOTPINK,
            Color.CRIMSON
        };

        shape.setFill(powerupColors[type.ordinal()]);
        shape.setStrokeType(StrokeType.INSIDE);
        shape.setStrokeWidth(2);
        shape.setStroke(Color.BLACK);
    }

    private void stylePaddle(Shape shape) {
        shape.setFill(Color.DARKGRAY);
        shape.setStrokeType(StrokeType.INSIDE);
        shape.setStrokeWidth(5);
        shape.setStroke(Color.BLACK);
    }
}
