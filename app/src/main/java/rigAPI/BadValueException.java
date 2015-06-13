package rigAPI;

public class BadValueException extends RiGException {
    public BadValueException(Exception e) {
        super(e);
    }

    public BadValueException() {
    }
}
