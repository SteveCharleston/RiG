package rigAPI;

public class MissingDayException extends RiGException {
    public MissingDayException(Exception e) {
        super(e);
    }

    public MissingDayException() {
    }
}
