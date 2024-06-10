package github.otowave.api.exceptions;

public class DuplicateMusicException extends RuntimeException {
    public DuplicateMusicException(int musicID) {
        super("Music with id [" + musicID + "] already on the songlist");
    }
}
