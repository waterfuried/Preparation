package exceptions;

public class NoSuchValueException extends RuntimeException {
    public NoSuchValueException(Object o) {
        throw new RuntimeException("No such value: "+o);
    }
}