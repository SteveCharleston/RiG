package rigAPI;

public class GroupOnlyException extends RiGException {
    public GroupOnlyException(Exception e) {
        super(e);
    }

    public GroupOnlyException() {
        super();
    }
}
