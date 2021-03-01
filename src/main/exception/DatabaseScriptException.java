package main.exception;

public class DatabaseScriptException extends RuntimeException {

    public DatabaseScriptException(String message) {
        super(message);
    }
}
