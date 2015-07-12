package rigAPI;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;

/**
 * Contains all elements returned by getSTatistic.php as fields
 */
public class RigStatistic
        extends ClassFromXML implements Serializable {
    private int round;
    private int remaining_bands;
    private boolean final_round;
    private int bands_all;
    private int bands_remaining;
    private int bands_out;
    private int bands_disqualified;
    private String userName;
    private int multiplier;
    private Boolean isGroupAccount;

    /**
     * Sets up the object according to the fields in the retrieved xml
     * @param doc the document returned from getStatistic.php
     */
    public RigStatistic(Document doc) {
        super(doc);

        Element e = (Element) doc
                .getElementsByTagName("current_round")
                .item(0);
        round = Integer.parseInt(e.getAttribute("round"));
        remaining_bands = Integer.parseInt(getContent("remaining_bands"));

        if (getContent("final").equals("no")) {
            final_round = false;
        } else {
            final_round = true;
        }

        bands_all = Integer.parseInt(getContent("all"));
        bands_remaining = Integer.parseInt(getContent("remaining"));
        bands_out = Integer.parseInt(getContent("out"));
        bands_disqualified = Integer.parseInt(getContent("disqualified"));

        userName = getContent("name");
        multiplier = Integer.parseInt(getContent("multiplier"));
        if (getContent("group").equals("no")) {
            isGroupAccount = false;
        } else {
            isGroupAccount = true;
        }
    }

    @Override
    public String toString() {
        return "RigStatistic{" +
                "round=" + round +
                ", remaining_bands=" + remaining_bands +
                ", final_round=" + final_round +
                ", bands_all=" + bands_all +
                ", bands_remaining=" + bands_remaining +
                ", bands_out=" + bands_out +
                ", bands_disqualified=" + bands_disqualified +
                ", userName='" + userName + '\'' +
                ", multiplier=" + multiplier +
                ", isGroupAccount=" + isGroupAccount +
                '}';
    }

    public int getRound() {
        return round;
    }

    public int getRemaining_bands() {
        return remaining_bands;
    }

    public boolean isFinal_round() {
        return final_round;
    }

    public int getBands_all() {
        return bands_all;
    }

    public int getBands_reamining() {
        return bands_remaining;
    }

    public int getBands_out() {
        return bands_out;
    }

    public int getBands_disqualified() {
        return bands_disqualified;
    }

    public int getBands_remaining() {
        return bands_remaining;
    }

    public String getUserName() {
        return userName;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public Boolean getIsGroupAccount() {
        return isGroupAccount;
    }
}
