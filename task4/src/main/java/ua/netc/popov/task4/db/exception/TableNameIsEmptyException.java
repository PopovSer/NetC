package ua.netc.popov.task4.db.exception;

public class TableNameIsEmptyException extends Exception {

    public TableNameIsEmptyException() {
    }

    public TableNameIsEmptyException(String message) {
        super(message);
    }
    
    
}
