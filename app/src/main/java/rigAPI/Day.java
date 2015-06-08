package rigAPI;

/**
 * Representation of the Days as given by the Documentation
 */
public enum Day {
    NODAY(0), FR(1), SA(2), FRSA(3);

    /**
     * Default Constructor needed to support value assignment to enum fields
     * @param i index of the field
     */
    Day(int i) {
    }
}
