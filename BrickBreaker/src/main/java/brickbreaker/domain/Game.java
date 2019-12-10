package brickbreaker.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javafx.geometry.Point2D;

public class Game {

    private List<Ball> balls;
    private Paddle paddle;
    private List<Brick> bricks;
    private List<Powerup> powerups;
    private LevelGenerator levelGenerator;
    private PowerupService powerupService;
    private int score;
    private int level;
    private int lives;
    private int bricksLeft;

    private static final double BALL_RADIUS = 10;
    private static final double BALL_STARTING_SPEED = 300;
    private static final double SUPER_BALL_RADIUS = 17;
    private static final double PADDLE_WIDTH = 150;
    private static final double PADDLE_HEIGHT = 30;
    private static final double WIDE_PADDLE_WIDTH = 250;
    private static final int PADDLE_SPEED = 600;

    public Game(List<Ball> balls, List<Brick> bricks, List<Powerup> powerups) {
        this.balls = balls;
        this.bricks = bricks;
        this.powerups = powerups;
        Random random = new Random();
        powerupService = new PowerupService(random, 8);
        levelGenerator = new LevelGenerator();
        score = 0;
        level = 1;
        lives = 3;
        paddle = new Paddle(PADDLE_WIDTH, PADDLE_HEIGHT);
        this.balls.add(new Ball(BALL_RADIUS, false));
        this.bricks.addAll(levelGenerator.generate(level));
        bricksLeft = levelGenerator.breakableBrickCount();
    }

    public List<Ball> getBalls() {
        return balls;
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
        bricks.clear();
        powerups.clear();
        balls.clear();
        bricks.addAll(levelGenerator.generate(level));
        bricksLeft = levelGenerator.breakableBrickCount();
        balls.add(new Ball(BALL_RADIUS, false));
        paddle.setWidth(PADDLE_WIDTH);
        powerupService.reset();
    }

    public void update(double dt) {
        moveBalls(dt);
        movePowerups(dt);
        handlePaddleCollision();
        handleBrickCollision();
        handlePowerupCollision();
    }

    public void moveBalls(double dt) {
        List<Ball> toBeRemoved = new ArrayList<>();
        balls.forEach(ball -> {
            ball.move(dt);
            if (!ball.inPlay()) {
                toBeRemoved.add(ball);
            }
        });
        balls.removeAll(toBeRemoved);
    }

    public void movePowerups(double dt) {
        List<Entity> toBeRemoved = new ArrayList<>();
        powerups.forEach(powerup -> {
            powerup.move(dt);
            if (!powerup.inPlay()) {
                toBeRemoved.add(powerup);
                powerupService.outOfBounds(powerup.getType());
            }
        });
        powerups.removeAll(toBeRemoved);
    }

    public void movePaddle(int direction, double dt) {
        if (direction < 0) {
            paddle.move(-PADDLE_SPEED, dt);
        } else if (direction > 0) {
            paddle.move(PADDLE_SPEED, dt);
        }
    }

    public void handlePaddleCollision() {
        balls.forEach(ball -> {
            if (ball.intersects(paddle) && ball.getMovement().getY() > 0) {
                Point2D movement = ball.getMovement();
                ball.setMovement(new Point2D(movement.getX(), -movement.getY()));
            }
        });
    }

    public void handleBrickCollision() {
        List<Entity> toBeRemoved = new ArrayList<>();
        for (Brick brick : bricks) {
            for (Ball ball : balls) {
                if (!ball.intersects(brick)) {
                    continue;
                }
                if (brickHit(brick, ball)) {
                    toBeRemoved.add(brick);
                }
                if (!ball.isUnstoppable()) {
                    bounceBall(brick, ball);
                }
            }
        }
        bricks.removeAll(toBeRemoved);
    }

    public boolean brickHit(Brick brick, Ball ball) {
        if (!brick.isBreakable() && !ball.isUnstoppable()) {
            return false;
        }
        brick.hit();
        if (brick.hitsLeft() == 0 || ball.isUnstoppable()) {
            if (brick.isBreakable()) {
                bricksLeft--;
            }
            score += 100 + brick.getType() * 10;
            Powerup powerup = powerupService.roll(
                    brick.getX() + brick.getWidth() / 2,
                    brick.getY()
            );
            if (powerup != null) {
                powerups.add(powerup);
            }
            return true;
        }
        return false;
    }

    public void bounceBall(Brick brick, Ball ball) {
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

    public void handlePowerupCollision() {
        List<Entity> toBeRemoved = new ArrayList<>();
        powerups.forEach(powerup -> {
            if (powerup.intersects(paddle)) {
                activatePower(powerup.getType());
                toBeRemoved.add(powerup);
            }
        });

        powerups.removeAll(toBeRemoved);
    }

    public void activatePower(PowerupType type) {
        powerupService.setActive(type);
        switch (type) {
            case EXTRA:
                extraBalls();
                break;
            case WIDE:
                widePaddle();
                break;
            case SUPER:
                superBall();
                break;
            case HEALTH:
                lives++;
                break;
        }
    }

    public void extraBalls() {
        boolean unstoppable = powerupService.isActive(PowerupType.SUPER);
        double radius = unstoppable ? SUPER_BALL_RADIUS : BALL_RADIUS;
        Ball left = new Ball(radius, unstoppable);
        Ball right = new Ball(radius, unstoppable);

        left.setPosition(
                paddle.getX() + paddle.getWidth() / 2,
                paddle.getY() - left.getRadius()
        );
        right.setPosition(
                paddle.getX() + paddle.getWidth() / 2,
                paddle.getY() - left.getRadius()
        );

        left.setMovement(new Point2D(-BALL_STARTING_SPEED, -BALL_STARTING_SPEED));
        right.setMovement(new Point2D(BALL_STARTING_SPEED, -BALL_STARTING_SPEED));

        balls.add(left);
        balls.add(right);
    }

    public void widePaddle() {
        paddle.setWidth(WIDE_PADDLE_WIDTH);
        paddle.move(-50, 1);
    }

    public void superBall() {
        balls.forEach(ball -> {
            ball.setUnstoppable(true);
            ball.setRadius(SUPER_BALL_RADIUS);
        });
    }

    public boolean inPlay() {
        if (!balls.isEmpty()) {
            return true;
        }
        lives--;
        return false;
    }

    public boolean isOver() {
        return lives <= 0;
    }

    public boolean levelCleared() {
        return bricksLeft == 0;
    }

    public void resetBall() {
        Ball ball = balls.get(0);
        ball.setPosition(
                paddle.getX() + paddle.getWidth() / 2,
                paddle.getY() - ball.getRadius()
        );
    }

    public void reset() {
        Ball ball = new Ball(BALL_RADIUS, false);
        ball.setPosition(
                paddle.getX() + paddle.getWidth() / 2,
                paddle.getY() - ball.getRadius()
        );
        balls.add(ball);
        paddle.resetPosition();
        paddle.setWidth(PADDLE_WIDTH);
        powerups.clear();
        powerupService.reset();
    }

    public void start() {
        double dxStart = ThreadLocalRandom
                .current()
                .nextDouble(-BALL_STARTING_SPEED, BALL_STARTING_SPEED);
        balls.get(0).setMovement(new Point2D(dxStart, -BALL_STARTING_SPEED));
    }

}
