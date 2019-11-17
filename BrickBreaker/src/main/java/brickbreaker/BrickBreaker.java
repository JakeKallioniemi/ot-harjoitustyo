package brickbreaker;

import brickbreaker.domain.Ball;
import brickbreaker.domain.Brick;
import brickbreaker.domain.LevelGenerator;
import brickbreaker.domain.Paddle;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class BrickBreaker extends Application {

    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;

    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    private Color[] brickColors;

    @Override
    public void init() {
        LevelGenerator generator = new LevelGenerator();
        ball = new Ball(15);
        paddle = new Paddle(190, 30);
        bricks = generator.generate(1);
        brickColors = new Color[]{
            Color.BLUE,
            Color.RED,
            Color.GREEN,
            Color.ORANGE,
            Color.YELLOW,
            Color.VIOLET
        };
    }

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        
        root.getChildren().add(paddle.getShape());
        root.getChildren().add(ball.getShape());
        
        for (Brick brick : bricks) {
            Shape shape = brick.getShape();
            shape.setFill(brickColors[brick.getType()]);
            shape.setStroke(Color.BLACK);
            root.getChildren().add(shape);
        }

        ball.setMovement(new Point2D(2, -2));
        
        Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT, Color.DARKGRAY);
        
        Set<KeyCode> keysPressed = new HashSet<>();

        scene.setOnKeyPressed(event -> {
            keysPressed.add(event.getCode());
        });

        scene.setOnKeyReleased(event -> {
            keysPressed.remove(event.getCode());
        });

        new AnimationTimer() {

            @Override
            public void handle(long currentTime) {
                if (keysPressed.contains(KeyCode.LEFT)) {
                    paddle.move(-10);
                }
                if (keysPressed.contains(KeyCode.RIGHT)) {
                    paddle.move(10);
                }
                ball.move();
            }
        }.start();

        stage.setTitle("BrickBreaker");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
