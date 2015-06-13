package rigAPI;

public class BandNonexistentException extends RiGException {
    public BandNonexistentException(Exception e) {
        super(e);
    }

    public BandNonexistentException() {
        super();
    }
}
