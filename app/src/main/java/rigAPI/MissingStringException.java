package rigAPI;

public class MissingStringException extends RiGException {
    public MissingStringException(Exception e) {
        super(e);
    }

    public MissingStringException() {
    }
}
