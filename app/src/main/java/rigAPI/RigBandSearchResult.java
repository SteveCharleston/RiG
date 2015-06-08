package rigAPI;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a search result retrieved from searchBand.php
 */
public class RigBandSearchResult extends ClassFromXML{
    private String searchString;
    private List<SearchResultBand> bands;

    /**
     * Sets up the object with values retrieved from searchBand.php
     * @param doc document returned by RiG server
     */
    public RigBandSearchResult(Document doc) {
        super(doc);

        Element e = (Element) doc
                .getElementsByTagName("rig_search")
                .item(0);

        searchString = e.getAttribute("string");

        bands = new ArrayList<SearchResultBand>();
        NodeList bandResults = doc.getElementsByTagName("band");

        for (int i = 0; i < bandResults.getLength(); i++) {
            Element bandElement = (Element) bandResults.item(i);
            int curId = Integer.parseInt(bandElement.getAttribute("id"));
            String curName = bandElement
                    .getElementsByTagName("name")
                    .item(0)
                    .getTextContent();

            bands.add(new SearchResultBand(curId, curName));
        }
    }

    @Override
    public String toString() {
        return "RigBandSearchResult{" +
                "searchString='" + searchString + '\'' +
                ", bands=" + bands +
                '}';
    }
}
