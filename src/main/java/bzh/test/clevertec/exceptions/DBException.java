package bzh.test.clevertec.exceptions;

public class DBException extends RuntimeException{
    public DBException(String message) {
        super(message);
    }
    public DBException(String message, Exception ex) {
        super(message, ex);
    }
}
