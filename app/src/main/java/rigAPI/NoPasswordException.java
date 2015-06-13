package rigAPI;

public class NoPasswordException extends RiGException {
    public NoPasswordException(Exception e) {
        super(e);
    }

    public NoPasswordException() {
    }
}
