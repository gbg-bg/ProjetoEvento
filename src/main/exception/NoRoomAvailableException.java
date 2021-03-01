package main.exception;

public class NoRoomAvailableException extends RuntimeException {

    public NoRoomAvailableException(String message) {
        super(message);
    }
}
