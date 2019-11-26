package brickbreaker.ui;

import static brickbreaker.BrickBreaker.GAME_HEIGHT;
import static brickbreaker.BrickBreaker.GAME_WIDTH;
import brickbreaker.domain.Brick;
import brickbreaker.domain.Game;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class GameView implements View {

    private ViewManager viewManager;
    private Game game;
    private AnimationTimer gameLoop;
    private Scene scene;

    public GameView(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void enter() {
        game = new Game();
        scene = createScene();
        game.start();
        gameLoop.start();
    }

    private Scene createScene() {
        Group root = new Group();

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

        Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT, Color.DARKGRAY);

        Set<KeyCode> keysPressed = new HashSet<>();

        scene.setOnKeyPressed(event -> {
            keysPressed.add(event.getCode());
        });

        scene.setOnKeyReleased(event -> {
            keysPressed.remove(event.getCode());
        });

        gameLoop = createGameLoop(keysPressed, root);

        return scene;
    }

    private AnimationTimer createGameLoop(Set<KeyCode> keysPressed, Group root) {

        AnimationTimer timer = new AnimationTimer() {

            long prevTime = System.nanoTime();

            @Override
            public void handle(long currentTime) {
                double dt = (currentTime - prevTime) / 1000000000.0;
                prevTime = currentTime;

                if (keysPressed.contains(KeyCode.LEFT)) {
                    game.movePaddle(-600, dt);
                }
                if (keysPressed.contains(KeyCode.RIGHT)) {
                    game.movePaddle(600, dt);
                }

                game.moveBall(dt);
                game.handlePaddleCollision();

                List<Brick> toBeRemoved = game.handleBrickCollision();
                if (toBeRemoved.size() > 0) {
                    toBeRemoved.forEach(brick -> {
                        root.getChildren().remove(brick.getShape());
                    });
                }

                if (game.gameOver()) {
                    game.restartBall();
                }
            }
        };
        return timer;
    }
}
