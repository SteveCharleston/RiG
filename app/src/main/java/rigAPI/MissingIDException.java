package rigAPI;

public class MissingIDException extends RiGException {
    public MissingIDException(Exception e) {
        super(e);
    }

    public MissingIDException() {
    }
}
