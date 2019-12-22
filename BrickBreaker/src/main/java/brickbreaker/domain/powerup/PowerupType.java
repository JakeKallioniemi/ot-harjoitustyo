package brickbreaker.domain.powerup;

public enum PowerupType {
    EXTRA(false),
    WIDE(true),
    SUPER(true),
    HEALTH(false);

    private boolean limited;

    private PowerupType(boolean limited) {
        this.limited = limited;
    }

    public boolean isLimited() {
        return limited;
    }
}
