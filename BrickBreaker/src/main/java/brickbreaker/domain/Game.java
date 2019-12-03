package brickbreaker.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javafx.geometry.Point2D;

public class Game {

    private Ball ball;
    private Paddle paddle;
    private List<Brick> bricks;
    private LevelGenerator generator;
    private int score;
    private int level;
    private int lives;

    public Game() {
        generator = new LevelGenerator();
        score = 0;
        level = 0;
        lives = 3;
    }

    public Ball getBall() {
        return ball;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public int getLives() {
        return lives;
    }

    public void newLevel() {
        level++;
        ball = new Ball(15);
        paddle = new Paddle(190, 30);
        bricks = generator.generate(level);
    }

    public void moveBall(double dt) {
        ball.move(dt);
    }

    public void movePaddle(double dx, double dt) {
        paddle.move(dx, dt);
    }

    public void handlePaddleCollision() {
        if (ball.intersects(paddle) && ball.getMovement().getY() > 0) {
            Point2D movement = ball.getMovement();
            ball.setMovement(new Point2D(movement.getX(), -movement.getY()));
        }
    }

    public List<Brick> handleBrickCollision() {
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

                score += 100 + brick.getType() * 10;
            }
        });

        bricks.removeAll(toBeRemoved);

        return toBeRemoved;
    }

    public boolean ballInPlay() {
        if (ball.inPlay()) {
            return true;
        }
        lives--;
        return false;
    }

    public boolean isOver() {
        return lives <= 0;
    }

    public boolean levelCleared() {
        return bricks.size() == 0;
    }

    public void resetBall() {
        ball.setPosition(
                paddle.getX() + paddle.getWidth() / 2,
                paddle.getY() - ball.getRadius()
        );
    }

    public void resetPaddle() {
        paddle.reset();
    }

    public void start() {
        double dxStart = ThreadLocalRandom.current().nextDouble(-300, 300);
        ball.setMovement(new Point2D(dxStart, -300));
    }

}
