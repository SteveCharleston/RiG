package rigAPI;

public class NoUserException extends RiGException {
    public NoUserException(Exception e) {
        super(e);
    }

    public NoUserException() {
        super();
    }
}
