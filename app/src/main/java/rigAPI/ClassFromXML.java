package rigAPI;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Common anchestor for all classes that parse an xml Document
 */
public class ClassFromXML {
    private Document doc;

    public ClassFromXML(Document doc) {
        this.doc = doc;
    }

    /**
     * Retrieves all childnodes of a given tagname
     * @param tagname the name of the tag whose child elements should be
     *                retrieved
     * @return        childelements of the given tag
     */
    public NodeList getChildEntities(String tagname) {
        return doc
                .getElementsByTagName(tagname)
                .item(0)
                .getChildNodes();
    }

    /**
     * Retrieves the Text content of the first element in the given document
     * with the given tagname
     * @param tagname   tagname whose inner text we want to get
     * @return          the inner text of the given tagname
     */
    public String getContent(String tagname) {
        return doc.getElementsByTagName(tagname).item(0).getTextContent();
    }

    public Document getDoc() {
        return doc;
    }
}
