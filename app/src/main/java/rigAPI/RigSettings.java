package rigAPI;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Contains all fields returned by getSettings.php as fields
 */
public class RigSettings extends  ClassFromXML {
    private String status;
    private Integer year;
    private int neccessary_votes;
    private List<Integer> neccessary_votes_special;
    private List<String> tags_voice;
    private List<String> tags_day;
    private List<String> tags_music;
    private int limit_vote_min;
    private int limit_vote_max;
    private int limit_tag_min;
    private int limit_tag_max;
    private int limit_day_min;
    private int limit_day_max;
    private int limit_bands;

    @Override
    public String toString() {
        return "RigSettings{" +
                "status='" + status + '\'' +
                ", year=" + year +
                ", neccessary_votes=" + neccessary_votes +
                ", neccessary_votes_special=" + neccessary_votes_special +
                ", tags_voice=" + tags_voice +
                ", tags_day=" + tags_day +
                ", tags_music=" + tags_music +
                ", limit_vote_min=" + limit_vote_min +
                ", limit_vote_max=" + limit_vote_max +
                ", limit_tag_min=" + limit_tag_min +
                ", limit_tag_max=" + limit_tag_max +
                ", limit_day_min=" + limit_day_min +
                ", limit_day_max=" + limit_day_max +
                ", limit_bands=" + limit_bands +
                '}';
    }

    /**
     * Sets up the object according to the fields in the retrieved xml
     * document after calling getSettings.php
     * @param doc the document returned from the RiG server
     */
    public RigSettings(Document doc) {
        super(doc);

        status = getContent("status");
        year = Integer.parseInt(getContent("year"));
        neccessary_votes = Integer.parseInt(getContent("necessary_votes"));

        neccessary_votes_special = new ArrayList<Integer>();
        NodeList specialVotes = getChildEntities("necessary_votes_special");
        for (int i = 0; i < specialVotes.getLength(); i++) {
            neccessary_votes_special.add(
                    Integer.parseInt(specialVotes.item(i).getTextContent()));
        }

        tags_voice = new ArrayList<String>();
        NodeList voiceEntities = getChildEntities("voice");
        for (int i = 0; i < voiceEntities.getLength(); i++) {
            tags_voice.add(voiceEntities.item(i).getTextContent());
        }

        tags_day = new ArrayList<String>();
        NodeList dayEntities = getChildEntities("day");
        for (int i = 0; i < dayEntities.getLength(); i++) {
            tags_day.add(dayEntities.item(i).getTextContent());
        }

        tags_music = new ArrayList<String>();
        NodeList musicEntities = getChildEntities("music");
        for (int i = 0; i < musicEntities.getLength(); i++) {
            tags_music.add(musicEntities.item(i).getTextContent());
        }

        String nodename;
        String localname;
        NodeList limitsNodes = getChildEntities("limits");
        Element limitsElement = (Element) limitsNodes;

        for (int i = 0; i < limitsNodes.getLength(); i++) {
            Element nodeElement = (Element) limitsNodes.item(i);
            if (nodeElement.getTagName().equals("vote")) {
                limit_vote_min = Integer.parseInt(
                        nodeElement.getChildNodes().item(0).getTextContent());
                limit_vote_max = Integer.parseInt(
                        nodeElement.getChildNodes().item(1).getTextContent());

            } else if (nodeElement.getTagName().equals("tag")) {
                limit_tag_min = Integer.parseInt(
                        nodeElement.getChildNodes().item(0).getTextContent());
                limit_tag_max = Integer.parseInt(
                        nodeElement.getChildNodes().item(1).getTextContent());

            } else if (nodeElement.getTagName().equals("day")) {
                limit_day_min = Integer.parseInt(
                        nodeElement.getChildNodes().item(0).getTextContent());
                limit_day_max = Integer.parseInt(
                        nodeElement.getChildNodes().item(1).getTextContent());

            } else if (nodeElement.getTagName().equals("bands")) {
                limit_bands = Integer.parseInt(nodeElement.getTextContent());
            }
        }
    }

    public String getStatus() {
        return status;
    }

    public Integer getYear() {
        return year;
    }

    public int getNeccessary_votes() {
        return neccessary_votes;
    }

    public List<Integer> getNeccessary_votes_special() {
        return neccessary_votes_special;
    }

    public List<String> getTags_voice() {
        return tags_voice;
    }

    public List<String> getTags_day() {
        return tags_day;
    }

    public List<String> getTags_music() {
        return tags_music;
    }

    public int getLimit_vote_min() {
        return limit_vote_min;
    }

    public int getLimit_vote_max() {
        return limit_vote_max;
    }

    public int getLimit_tag_min() {
        return limit_tag_min;
    }

    public int getLimit_tag_max() {
        return limit_tag_max;
    }

    public int getLimit_day_min() {
        return limit_day_min;
    }

    public int getLimit_day_max() {
        return limit_day_max;
    }

    public int getLimit_bands() {
        return limit_bands;
    }
}
