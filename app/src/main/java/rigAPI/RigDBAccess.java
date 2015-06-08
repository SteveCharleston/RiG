/**
 *
 */
package rigAPI;


import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Steven Schalhorn
 *
 */
public class RigDBAccess {

    private static String API_KEY = null;
    private static final String APIURL
            = "http://bewerbung.rockimgruenen.de/api/";

    /**
     * @param args Arguments are currently unused
     */
    public static void main(String[] args) throws RiGException {
    }

    /**
     * Authenticates an user against the RiG servers. Saves the API-Key
     * internally so that other methods don't have to care about
     * authentication. Has to be called as first method after instantiating
     * this class.
     *
     * @param user          username of an RiG user
     * @param password      corresponding password of the RiG user
     * @return              returns the obtained API-Key, even though it is also
     *                      saved internally
     * @throws RiGException the errors described in the API-Documentation as
     *                      exceptions
     */
    public String authenticate(String user, String password) throws
            RiGException {
        String pageURL = APIURL + "authenticate.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user", user));
        params.add(new BasicNameValuePair("password", password));

        String result = httpPost(pageURL, params);

        if ("NO_USER".equals(result)) {
            throw new NoUserException();
        } else if ("NO_PASSWORD".equals(result)) {
            throw new NoPasswordException();
        } else if ("BAD_AUTHENTICATION".equals(result)) {
            throw new BadAuthenticationException();
        } else if ("BROKEN_APIKEY".equals(result)) {
            throw new BrokenAPIKeyException();
        }

        API_KEY = result;

        return result;
    }

    /**
     * Retrieves band informations of a random band from the RiG server
     * @return              object with all band informations as fields
     * @throws RiGException several subclasses of RiGException
     */
    public RigBand getBand() throws RiGException {
        return getBand(null);
    }

    /**
     * Retrieves band informations from getBand.php
     * @param band          numerical id of a specific band
     * @return              object with all band informations as fields
     * @throws RiGException several subclasses of RiGException
     */
    public RigBand getBand(Integer band) throws RiGException {
        String pageURL = APIURL + "read/getBand.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if (band != null) {
            params.add(new BasicNameValuePair("band", band.toString()));
        }

        String result = httpPost(pageURL, params);

        if ("BAD_BAND".equals(result)) {
            throw new BadBandException();
        } else if ("GROUP_ONLY".equals(result)) {
            throw new GroupOnlyException();
        } else if ("BAND_NONEXISTANT".equals(result)) {
            throw new BandNonexistentException();
        } else if ("ROUND_COMPLETED".equals(result)) {
            throw new RoundCompletedException();
        }

        Document doc = getDocumentFromXMLString(result);
        return new RigBand(doc);
    }

    /**
     * Retrieves settings from getSettings.php
     * @return              object with all settings as fields
     * @throws RiGException several subclasses of RiGException
     */
    public RigSettings getSettings() throws RiGException {
        String pageURL = APIURL + "read/getSettings.php";
        String result = httpPost(pageURL);

        Document doc = getDocumentFromXMLString(result);

        return new RigSettings(doc);
    }

    /**
     * Retrieves statistics from getStatistic.php
     * @return              object with all statistics as fields
     * @throws RiGException several subclasses of RiGException
     */
    public RigStatistic getStatistic() throws RiGException {
        String pageUrl = APIURL + "read/getStatistic.php";
        String result = httpPost(pageUrl);

        Document doc = getDocumentFromXMLString(result);

        return new RigStatistic(doc);
    }

    /**
     * Retrieves the toplist from getToplist.php
     * @param day  Day to show the toplist for
     * @return RigToplist instance
     * @throws MissingDayException is thrown if no day is given
     * @throws BadDayException is thrown when day is not FR or SA
     * @throws RiGException is thrown when XML parsing fails
     */
    public RigToplist getToplist(Day day)
            throws RiGException {
        String pageUrl = APIURL + "read/getToplist.php";

        Integer dayInt = day.ordinal();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("day", dayInt.toString()));

        String result = httpPost(pageUrl, params);

        if ("MISSING_DAY".equals(result)) {
            throw new MissingDayException();
        } else if ("BAD_DAY".equals(result)) {
            throw new BadDayException();
        }

        Document doc = getDocumentFromXMLString(result);
        return new RigToplist(doc);
    }

    /**
     * Retrieves a search result from searchBand.php
     * @param bandname pattern to search for
     * @return RigBandSearchResult instance
     * @throws MissingStringException Search string is too short
     * @throws RiGException
     */
    public RigBandSearchResult searchBand(String bandname)
        throws RiGException {
        String pageUrl = APIURL + "read/searchBand.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("string", bandname));

        String result = httpPost(pageUrl, params);

        if ("MISSING_STRING".equals(result)) {
            throw new MissingStringException();
        }

        Document doc = getDocumentFromXMLString(result);
        return new RigBandSearchResult(doc);
    }

    /**
     * Returns a download as String
     * @param download File-ID as noted in getBand.php
     * @return         String represantation of downloaded file
     * @throws httpPostException Errors while downloading
     * @throws MissingIDException If no download id is given
     * @throws BadIDException if an illigal download id is given
     */
    public String downloadFile(Integer download) throws RiGException {
        String pageUrl = APIURL + "read/downloadFile.php?";
        pageUrl = pageUrl
                + "apikey=" + API_KEY
                + "&download=" + download.toString();

        String result = httpPost(pageUrl);

        if ("MISSING_ID".equals(result)) {
            throw new MissingIDException();
        } else if ("BAD_ID".equals(result)) {
            throw new BadIDException();
        }

        return result;
    }

    /**
     * Sets the Weekday for a band
     * @param band numerical Band id as specified by getBand.php
     * @param day Day to set the band to
     * @return OK on success
     * @throws RiGException General error during execution
     * @throws MissingBandException No Band-ID has been given
     * @throws MissingValueException No Day has been given
     * @throws BadValueException Day is not in acceptable range
     * @throws NotInRoundException Given band is not in current round so it
     *                             isn't votable
     * @throws GroupOnlyException Last round has been started, only group
     * accounts are able to vote
     */
    public String setDay(Integer band, Day day) throws RiGException {
        String pageUrl = APIURL + "write/setDay.php";

        Integer dayVal = day.ordinal();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("band", band.toString()));
        params.add(new BasicNameValuePair("value", dayVal.toString()));

        String result = httpPost(pageUrl, params);

        if ("MISSING_BAND".equals(result)) {
            throw new MissingBandException();
        } else if ("MISSING_VALUE".equals(result)) {
            throw new MissingValueException();
        } else if ("BAD_VALUE".equals(result)) {
            throw new BadValueException();
        } else if ("NOT_IN_ROUND".equals(result)) {
            throw new NotInRoundException();
        } else if ("GROUP_ONLY".equals(result)) {
            throw new GroupOnlyException();
        }

        return result;
    }

    /**
     * Sets a tag for the given band
     * @param band numerical Band id as specified by getBand.php
     * @param musikstil tag to set for the band to as defined by getSettings.php
     * @return OK if success
     * @throws RiGException General error during Execution
     * @throws MissingBandException No Band-ID has been given
     * @throws MissingValueException No Day has been given
     * @throws BadValueException Day is not in acceptable range
     * @throws NotInRoundException Given band is not in current round so it
     *                             isn't votable
     * @throws GroupOnlyException Last round has been started, only group
     * accounts are able to vote
     */
    public String setTag(Integer band, Integer musikstil) throws RiGException {
        String pageUrl = APIURL + "write/setTag.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("band", band.toString()));
        params.add(new BasicNameValuePair("value", musikstil.toString()));

        String result = httpPost(pageUrl, params);

        if ("MISSING_BAND".equals(result)) {
            throw new MissingBandException();
        } else if ("MISSING_VALUE".equals(result)) {
            throw new MissingValueException();
        } else if ("BAD_VALUE".equals(result)) {
            throw new BadValueException();
        } else if ("NOT_IN_ROUND".equals(result)) {
            throw new NotInRoundException();
        } else if ("GROUP_ONLY".equals(result)) {
            throw new GroupOnlyException();
        }

        return result;
    }

    /**
     * Votes for a given band
     * @param band numerical Band id as specified by getBand.php
     * @param rating submit the rating for the band
     * @return OK if success
     * @throws RiGException General error during Execution
     * @throws MissingBandException No Band-ID has been given
     * @throws MissingValueException No Day has been given
     * @throws BadValueException Day is not in acceptable range
     * @throws NotInRoundException Given band is not in current round so it
     *                             isn't votable
     * @throws GroupOnlyException Last round has been started, only group
     * accounts are able to vote
     */
    public String vote(Integer band, Integer rating) throws RiGException {
        String pageUrl = APIURL + "write/vote.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("band", band.toString()));
        params.add(new BasicNameValuePair("value", rating.toString()));

        String result = httpPost(pageUrl, params);

        if ("MISSING_BAND".equals(result)) {
            throw new MissingBandException();
        } else if ("MISSING_VALUE".equals(result)) {
            throw new MissingValueException();
        } else if ("BAD_VALUE".equals(result)) {
            throw new BadValueException();
        } else if ("NOT_IN_ROUND".equals(result)) {
            throw new NotInRoundException();
        } else if ("GROUP_ONLY".equals(result)) {
            throw new GroupOnlyException();
        }

        return result;
    }

    /**
     * Constructs a Document from a given valid xml String
     * @param xmlString     String conatining a valid xml document
     * @return              Document generated from given xmlString
     * @throws RiGException Any errors occuring during parsing of xmlString
     */
    private static Document getDocumentFromXMLString(String xmlString)
            throws RiGException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new rigGetBandException(e);
        }

        StringBuilder xmlStringBuilder = new StringBuilder(xmlString);
        ByteArrayInputStream input;
        try {
            input = new ByteArrayInputStream
                    (xmlStringBuilder.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RiGException(e);
        }
        Document doc;
        try {
            doc = builder.parse(input);
        } catch (IOException e) {
            throw new RiGException(e);
        } catch (SAXException e) {
            throw new RiGException(e);
        }

        return doc;
    }

    /**
     * Gets the content of a given url
     * @param url                the url of the requested page
     * @return                   returned content from server
     * @throws httpPostException the possible IOExceptions and other exceptions,
     *                           that coule happen during communication are
     *                           wrapped
     */
    private static String httpPost(String url) throws httpPostException {
        return httpPost(url, null);
    }

    /**
     * Sends a list of parameters to a given URL and returns the response.
     * @param url                the url of the requested page
     * @param urlParameters      post parameters to be send to given url
     * @return                   Returned content from server
     * @throws httpPostException the possible IOExceptions and other exceptions,
     *                           that coule happen during communication are
     *                           wrapped
     */
    private static String httpPost(String url,
                                   List<NameValuePair> urlParameters)
            throws httpPostException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        if (urlParameters == null) {
            urlParameters = new ArrayList<NameValuePair>();
        }

        String USER_AGENT = "Mozilla/5.0";
        post.setHeader("User-Agent", USER_AGENT);

        urlParameters.add(new BasicNameValuePair("apikey", API_KEY));
        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
        } catch (IOException e) {
            throw new httpPostException(e);
        }

        HttpResponse response;
        try {
            response = client.execute(post);
        } catch (IOException e) {
            throw new httpPostException(e);
        }

        BufferedReader rd;
        try {
            rd = new BufferedReader(new InputStreamReader
                    (response.getEntity().getContent()));
        } catch (IOException e) {
            throw new httpPostException(e);
        }

        StringBuilder result = new StringBuilder();
        String line;
        try {
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            throw new httpPostException(e);
        }

        return result.toString();
    }

}
class RiGException extends Exception {
    public RiGException(Exception e) {
        super(e);
    }

    public RiGException() {
    }
}

class RoundCompletedException extends RiGException {
    public RoundCompletedException(Exception e) {
        super(e);
    }

    public RoundCompletedException() {
        super();
    }
}

class BandNonexistentException extends RiGException {
    public BandNonexistentException(Exception e) {
        super(e);
    }

    public BandNonexistentException() {
        super();
    }
}

class GroupOnlyException extends RiGException {
    public GroupOnlyException(Exception e) {
        super(e);
    }

    public GroupOnlyException() {
        super();
    }
}

class BadBandException extends RiGException {
    public BadBandException(Exception e) {
        super(e);
    }

    public BadBandException() {
        super();
    }
}

class NoUserException extends RiGException {
    public NoUserException(Exception e) {
        super(e);
    }

    public NoUserException() {
        super();
    }
}

class NoPasswordException extends RiGException {
    public NoPasswordException(Exception e) {
        super(e);
    }

    public NoPasswordException() {
    }
}

class BadAuthenticationException extends RiGException {
    public BadAuthenticationException(Exception e) {
        super(e);
    }

    public BadAuthenticationException() {
    }
}

class BrokenAPIKeyException extends RiGException {
    public BrokenAPIKeyException(Exception e) {
        super(e);
    }

    public BrokenAPIKeyException() {
    }
}

class httpPostException extends RiGException {
    httpPostException(Exception e) {
        super(e);
    }
}

class rigGetBandException extends RiGException {
    rigGetBandException(Exception e) {
        super(e);
    }
}

class MissingDayException extends RiGException {
    public MissingDayException(Exception e) {
        super(e);
    }

    public MissingDayException() {
    }
}

class BadDayException extends RiGException {
    public BadDayException(Exception e) {
        super(e);
    }

    public BadDayException() {
    }
}

class MissingStringException extends RiGException {
    public MissingStringException(Exception e) {
        super(e);
    }

    public MissingStringException() {
    }
}

class MissingIDException extends RiGException {
    public MissingIDException(Exception e) {
        super(e);
    }

    public MissingIDException() {
    }
}

class BadIDException extends RiGException {
    public BadIDException(Exception e) {
        super(e);
    }

    public BadIDException() {
    }
}

class MissingBandException extends RiGException {
    public MissingBandException(Exception e) {
        super(e);
    }

    public MissingBandException() {
    }
}

class MissingValueException extends RiGException {
    public MissingValueException(Exception e) {
        super(e);
    }

    public MissingValueException() {
    }
}

class NotInRoundException extends RiGException {
    public NotInRoundException(Exception e) {
        super(e);
    }

    public NotInRoundException() {
    }
}

class BadValueException extends RiGException {
    public BadValueException(Exception e) {
        super(e);
    }

    public BadValueException() {
    }
}
