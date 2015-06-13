package rigAPI;

public class RoundCompletedException extends RiGException {
    public RoundCompletedException(Exception e) {
        super(e);
    }

    public RoundCompletedException() {
        super();
    }
}
