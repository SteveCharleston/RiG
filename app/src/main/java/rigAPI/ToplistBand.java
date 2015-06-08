package rigAPI;

/**
 * Represents a band as returned from getToplist.php
 */
public class ToplistBand {
    private int id;
    private String name;
    private String musikstil;
    private Day day;
    private double result;

    /**
     * Creates a new ToplistBand
     * @param id        numerical id of the band
     * @param name      bandname
     * @param musikstil music style
     * @param day       Day of the week the band is favored for
     * @param result    numerical result
     */
    public ToplistBand(int id, String name, String musikstil, Day day,
                       double result) {
        this.id = id;
        this.name = name;
        this.musikstil = musikstil;
        this.day = day;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMusikstil() {
        return musikstil;
    }

    public Day getDay() {
        return day;
    }

    public double getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "ToplistBand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", musikstil='" + musikstil + '\'' +
                ", day=" + day +
                ", result=" + result +
                '}';
    }
}
