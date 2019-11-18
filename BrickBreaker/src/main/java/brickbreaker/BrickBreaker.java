package brickbreaker;

import brickbreaker.domain.Ball;
import brickbreaker.domain.Brick;
import brickbreaker.domain.LevelGenerator;
import brickbreaker.domain.Paddle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

// will split into multiple classes later
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

        bricks.forEach(brick -> {
            Shape shape = brick.getShape();
            shape.setFill(brickColors[brick.getType()]);
            shape.setStroke(Color.BLACK);
            root.getChildren().add(shape);
        });

        double dxStart = ThreadLocalRandom.current().nextDouble(-3, 3);

        ball.setMovement(new Point2D(dxStart, -3));

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

                if (ball.intersects(paddle) && ball.getMovement().getY() > 0) {
                    Point2D movement = ball.getMovement();
                    ball.setMovement(new Point2D(movement.getX(), -movement.getY()));
                }

                List<Brick> toBeRemoved = new ArrayList<>();
                bricks.forEach(brick -> {
                    if (ball.intersects(brick)) {
                        toBeRemoved.add(brick);

                        Point2D movement = ball.getMovement();

                        // doesn't always work, fix later
                        if (ball.getX() + ball.getRadius() < brick.getX()
                                && movement.getX() > 0) {
                            ball.setMovement(new Point2D(-movement.getX(), movement.getY()));
                        } else if (ball.getX() - ball.getRadius() > brick.getX() + brick.getWidth()
                                && movement.getX() < 0) {
                            ball.setMovement(new Point2D(-movement.getX(), movement.getY()));
                        } else if (ball.getY() > brick.getY() && movement.getY() < 0) {
                            ball.setMovement(new Point2D(movement.getX(), -movement.getY()));
                        } else if (ball.getY() < brick.getY() && movement.getY() > 0) {
                            ball.setMovement(new Point2D(movement.getX(), -movement.getY()));
                        }
                    }
                });

                bricks.removeAll(toBeRemoved);
                toBeRemoved.forEach(brick -> {
                    root.getChildren().remove(brick.getShape());
                });
                
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
