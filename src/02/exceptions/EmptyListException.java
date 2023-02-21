package exceptions;

public class EmptyListException extends RuntimeException {
    public EmptyListException() { throw new RuntimeException("List is empty"); }
}
