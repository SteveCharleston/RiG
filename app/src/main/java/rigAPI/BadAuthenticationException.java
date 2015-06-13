package rigAPI;

/**
 * Created by steven on 13.06.15.
 */
public class BadAuthenticationException extends RiGException {
    public BadAuthenticationException(Exception e) {
        super(e);
    }

    public BadAuthenticationException() {
    }
}
