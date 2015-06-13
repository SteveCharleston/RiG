package rigAPI;

public class BadIDException extends RiGException {
    public BadIDException(Exception e) {
        super(e);
    }

    public BadIDException() {
    }
}
