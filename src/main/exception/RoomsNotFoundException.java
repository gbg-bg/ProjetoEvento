package main.exception;

public class RoomsNotFoundException extends RuntimeException {

    public RoomsNotFoundException(String message) {
        super(message);
    }
}
