package brickbreaker.domain;

import brickbreaker.domain.powerup.PowerupType;
import brickbreaker.domain.powerup.Powerup;
import brickbreaker.domain.powerup.PowerupService;
import java.util.ArrayList;
import java.util.List;
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
    private static final double BALL_STARTING_SPEED = 400;
    private static final double SUPER_BALL_RADIUS = 17;
    private static final double PADDLE_WIDTH = 150;
    private static final double PADDLE_HEIGHT = 30;
    private static final double WIDE_PADDLE_WIDTH = 250;
    private static final int PADDLE_SPEED = 600;

    /**
     * Creates a new Game, which manages the state of the game.
     *
     * @param balls list for balls
     * @param bricks list for bricks
     * @param powerups list for powerups
     * @param powerupService service for powerup spawning
     * @param levelGenerator service for generating levels
     */
    public Game(List<Ball> balls, List<Brick> bricks, List<Powerup> powerups,
            PowerupService powerupService, LevelGenerator levelGenerator) {

        this.balls = balls;
        this.bricks = bricks;
        this.powerups = powerups;
        this.powerupService = powerupService;
        this.levelGenerator = levelGenerator;
        score = 0;
        level = 1;
        lives = 3;
        paddle = new Paddle(PADDLE_WIDTH, PADDLE_HEIGHT);
        this.balls.add(new Ball(BALL_RADIUS, false));
        this.bricks.addAll(levelGenerator.generate());
        bricksLeft = levelGenerator.getBreakableBrickCount();
    }

    public Paddle getPaddle() {
        return paddle;
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

    /**
     * Resets entities and initializes a new level.
     */
    public void newLevel() {
        level++;
        bricks.clear();
        bricks.addAll(levelGenerator.generate());
        bricksLeft = levelGenerator.getBreakableBrickCount();
        resetMovableEntities();
    }

    /**
     * Moves objects and checks and handles collisions.
     *
     * @param dt time passed since last update
     */
    public void update(double dt) {
        moveBalls(dt);
        movePowerups(dt);
        handlePaddleCollision();
        handleBrickCollision();
        handlePowerupCollision();
    }

    /**
     * Moves the paddle by PADDLE_SPEED. Left if direction is negative, right if
     * positive.
     *
     * @param direction the direction of movement
     * @param dt time passed since last update
     */
    public void movePaddle(int direction, double dt) {
        if (direction < 0) {
            paddle.move(-PADDLE_SPEED, dt);
        } else if (direction > 0) {
            paddle.move(PADDLE_SPEED, dt);
        }
    }

    private void moveBalls(double dt) {
        List<Ball> toBeRemoved = new ArrayList<>();
        balls.forEach(ball -> {
            ball.move(dt);
            if (!ball.inPlay()) {
                toBeRemoved.add(ball);
            }
        });
        balls.removeAll(toBeRemoved);
    }

    private void movePowerups(double dt) {
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

    private void handlePaddleCollision() {
        balls.forEach(ball -> {
            if (ball.intersects(paddle) && ball.getMovement().getY() > 0) {
                double xMovement = bounceFromPaddle(ball);
                Point2D movement = ball.getMovement();
                ball.setMovement(new Point2D(xMovement, -movement.getY()));
            }
        });
    }

    private double bounceFromPaddle(Ball ball) {
        double zoneEnd = paddle.getWidth() / 5;
        double ballX = ball.getX();
        double paddleX = paddle.getX();
        double xMovement;

        // The paddle is split into 5 zones that change the way the ball bounces
        if (ballX < paddleX + zoneEnd) {
            xMovement = -300;
        } else if (ballX < paddleX + zoneEnd * 2) {
            xMovement = -150;
        } else if (ballX < paddleX + zoneEnd * 3) {
            xMovement = 0;
        } else if (ballX < paddleX + zoneEnd * 4) {
            xMovement = 150;
        } else {
            xMovement = 300;
        }
        return xMovement;
    }

    private void handleBrickCollision() {
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
                    bounceFromBrick(brick, ball);
                }
            }
        }
        bricks.removeAll(toBeRemoved);
    }

    private boolean brickHit(Brick brick, Ball ball) {
        if (!brick.isBreakable() && !ball.isUnstoppable()) {
            return false;
        }
        brick.hit();
        if (brick.hitsLeft() == 0 || ball.isUnstoppable()) {
            if (brick.isBreakable()) {
                bricksLeft--;
            }
            score += 50 + brick.getType() * 10;
            rollForPowerup(brick.getX() + brick.getWidth() / 2, brick.getY());
            return true;
        }
        return false;
    }

    private void rollForPowerup(double x, double y) {
        Powerup powerup = powerupService.roll(x, y);
        if (powerup != null) {
            powerups.add(powerup);
        }
    }

    private void bounceFromBrick(Brick brick, Ball ball) {
        Point2D movement = ball.getMovement();

        if (ball.getX() < brick.getX() && movement.getX() > 0) {
            ball.setMovement(new Point2D(-movement.getX(), movement.getY()));
        } else if (ball.getX() - ball.getRadius() >= brick.getX() + brick.getWidth()
                && movement.getX() < 0) {
            ball.setMovement(new Point2D(-movement.getX(), movement.getY()));
        } else if (ball.getY() > brick.getY() && movement.getY() < 0) {
            ball.setMovement(new Point2D(movement.getX(), -movement.getY()));
        } else if (ball.getY() < brick.getY() && movement.getY() > 0) {
            ball.setMovement(new Point2D(movement.getX(), -movement.getY()));
        }
    }

    private void handlePowerupCollision() {
        List<Entity> toBeRemoved = new ArrayList<>();
        powerups.forEach(powerup -> {
            if (powerup.intersects(paddle)) {
                activatePower(powerup.getType());
                toBeRemoved.add(powerup);
            }
        });

        powerups.removeAll(toBeRemoved);
    }

    private void activatePower(PowerupType type) {
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

    private void extraBalls() {
        boolean unstoppable = powerupService.isActive(PowerupType.SUPER);
        double radius = unstoppable ? SUPER_BALL_RADIUS : BALL_RADIUS;
        balls.add(extraBall(-BALL_STARTING_SPEED, radius, unstoppable));
        balls.add(extraBall(BALL_STARTING_SPEED, radius, unstoppable));
    }

    private Ball extraBall(double xSpeed, double radius, boolean unstoppable) {
        Ball ball = new Ball(radius, unstoppable);
        ball.setPosition(
                paddle.getX() + paddle.getWidth() / 2,
                paddle.getY() - ball.getRadius()
        );
        ball.setMovement(new Point2D(xSpeed, -BALL_STARTING_SPEED));
        return ball;
    }

    private void widePaddle() {
        paddle.setWidth(WIDE_PADDLE_WIDTH);
        paddle.move(-50, 1);
    }

    private void superBall() {
        balls.forEach(ball -> {
            ball.setUnstoppable(true);
            ball.setRadius(SUPER_BALL_RADIUS);
        });
    }

    /**
     * Checks if there are any balls currently in play.
     *
     * @return true if one or more balls in play, otherwise false
     */
    public boolean inPlay() {
        if (!balls.isEmpty()) {
            return true;
        }
        lives--;
        return false;
    }

    /**
     * Checks if the game is over (0 lives left).
     *
     * @return true if game is over, otherwise false
     */
    public boolean isOver() {
        return lives <= 0;
    }

    /**
     * Checks if the level is cleared.
     *
     * @return true if level is cleared, otherwise false
     */
    public boolean levelCleared() {
        return bricksLeft == 0;
    }

    /**
     * Changes the balls position to the center of the paddle horizontally and
     * just above the paddle vertically.
     */
    public void centerBallOnPaddle() {
        Ball ball = balls.get(0);
        ball.setPosition(
                paddle.getX() + paddle.getWidth() / 2,
                paddle.getY() - ball.getRadius()
        );
    }

    /**
     * Resets balls, paddle and powerups to the state they were in the beginning
     * of level.
     */
    public void resetMovableEntities() {
        paddle.resetPosition();
        paddle.setWidth(PADDLE_WIDTH);
        balls.clear();
        balls.add(new Ball(BALL_RADIUS, false));
        powerups.clear();
        powerupService.reset();
    }

    /**
     * Gives the ball a starting speed. Horizontal speed is randomly chosen
     * between negative and positive BALL_STARTING_SPEED.
     */
    public void start() {
        double dxStart = ThreadLocalRandom
                .current()
                .nextDouble(-BALL_STARTING_SPEED, BALL_STARTING_SPEED);
        balls.get(0).setMovement(new Point2D(dxStart, -BALL_STARTING_SPEED));
    }

}
