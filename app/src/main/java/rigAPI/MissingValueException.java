package rigAPI;

public class MissingValueException extends RiGException {
    public MissingValueException(Exception e) {
        super(e);
    }

    public MissingValueException() {
    }
}
