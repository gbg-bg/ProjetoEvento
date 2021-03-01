package main.util;

public class MessageUtil {

    private MessageUtil() {
        throw new IllegalStateException();
    }

    public static final String INSERT_SUCCESS = "Record Inserted Successfully!!!";
    public static final String INSERT_FAILED = "Record Can't Inserted!!!";
    public static final String INSERT_ERROR = "Error when register record!!!";
    public static final String RECORD_CONFLICT = "The record already exists!!!";
    public static final String RECORD_NOT_FOUND = "Record Not Found For Given ID!!!";
    public static final String NO_ROOMS_AVAILABLE = "There is no room with capacity available!!!";
    public static final String ROOMS_NOT_FOUND = "There is no registered rooms!!!";
    public static final String DATABASE_ERROR = "Error when running database scripts!!!";
}
