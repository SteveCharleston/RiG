package rigAPI;

public class NotInRoundException extends RiGException {
    public NotInRoundException(Exception e) {
        super(e);
    }

    public NotInRoundException() {
    }
}
