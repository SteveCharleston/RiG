package rigAPI;

public class BrokenAPIKeyException extends RiGException {
    public BrokenAPIKeyException(Exception e) {
        super(e);
    }

    public BrokenAPIKeyException() {
    }
}
