package rigAPI;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a band as returned from getToplist.php
 */
public class ToplistBand implements Serializable {
    private int id;
    private String name;
    private String voice;
    private List musikStile;
    private Day day;
    private double result;

    /**
     * Creates a new ToplistBand
     * @param id        numerical id of the band
     * @param name      bandname
     * @param voice     Geschlecht des Gesangs
     * @param musikStile music style
     * @param day       Day of the week the band is favored for
     * @param result    numerical result
     */
    public ToplistBand(int id, String name, String voice, List musikStile,
                       Day day,
                       double result) {
        this.id = id;
        this.name = name;
        this.voice = voice;
        this.musikStile = musikStile;
        this.day = day;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVoice() {
        return voice;
    }

    public List getMusikstile() {
        return musikStile;
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
                ", voice='" + voice + '\'' +
                ", musikStile='" + musikStile + '\'' +
                ", day=" + day +
                ", result=" + result +
                '}';
    }
}
