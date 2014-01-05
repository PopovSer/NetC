package ua.netc.popov.task4.db.exception;

public class EmptyParameterException extends Exception{

    public EmptyParameterException() {
        super();
    }

    public EmptyParameterException(String message) {
        super(message);
    }
}
