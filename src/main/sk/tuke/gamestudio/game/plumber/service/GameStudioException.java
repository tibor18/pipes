package sk.tuke.gamestudio.game.plumber.service;

public class GameStudioException extends RuntimeException{
    public GameStudioException() {
    }

    public GameStudioException(Throwable cause) {
        super(cause);
    }

    public GameStudioException(String message) {
        super(message);
    }

    public GameStudioException(String message, Throwable cause) {
        super(message, cause);
    }
}
