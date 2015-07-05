package rigAPI;

/**
 * Created by steven on 05.07.15.
 */
public class BadAPIKeyException extends RiGException {
    public BadAPIKeyException(Exception e) {
        super(e);
    }

    public BadAPIKeyException() {
    }
}
