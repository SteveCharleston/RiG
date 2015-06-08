package rigAPI;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import rigAPI.Day;
import rigAPI.ToplistBand;

/**
 * Contains all values returned by getToplist.php as fields
 */
public class RigToplist extends ClassFromXML {
    private Day day;
    private List<ToplistBand> bands;

    /**
     * Sets up the object according to the fields in the retrieved xml
     * document after calling getToplist.php
     * @param doc the document returned from getToplist.php
     */
    public RigToplist(Document doc) {
        super(doc);

        Element e = (Element) doc
                .getElementsByTagName("rig_toplist")
                .item(0);

        String elementDay = e.getAttribute("day");
        day = parseDay(elementDay);

        bands = new ArrayList<ToplistBand>();
        NodeList bandNodes = doc.getElementsByTagName("band");

        for (int i = 0; i < bandNodes.getLength(); i++) {
            Element bandElement = (Element) bandNodes.item(i);
            int curId = Integer.parseInt(bandElement.getAttribute("id"));
            String curName = bandElement
                    .getElementsByTagName("name")
                    .item(0)
                    .getTextContent();

            String curMusikstil = bandElement
                    .getElementsByTagName("musikstil")
                    .item(0)
                    .getTextContent();

            Day curDay = parseDay(
                    bandElement
                            .getElementsByTagName("day")
                            .item(0)
                            .getTextContent());

            double curResult = Double.parseDouble(
                    bandElement
                    .getElementsByTagName("result")
                    .item(0)
                    .getTextContent());

            bands.add(new ToplistBand(
                            curId,
                            curName,
                            curMusikstil,
                            curDay,
                            curResult));
        }
    }

    /**
     * Parses a day String and returns a value from the Day enum
     * @param day String of the Day to interpret
     * @return    Value corresponding to day as defined in Day enum
     */
    private Day parseDay(String day) {
        if ("FR".equals(day)) {
            return Day.FR;
        } else if ("SA".equals(day)) {
            return Day.SA;
        } else {
            return Day.FRSA;
        }
    }

    @Override
    public String toString() {
        return "RigToplist{" +
                "day=" + day +
                ", bands=" + bands +
                '}';
    }
}
