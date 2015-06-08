package rigAPI;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all elements returned by getBand.php as fields
 */
public class RigBand extends ClassFromXML {
    private Integer id;
    private Integer bewerbungsdatum;
    private String uid;
    private String status;
    private int runde;
    private int result;
    private String day;
    private String name;
    private String proberaum;
    private List<String> beschreibung;
    private List<String> besetzung;
    private String musikstil;
    private String voice;
    private String gruendung;
    private String covermusik;
    private int titel;
    private boolean gema;
    private String kontakt_nachname;
    private String kontakt_vorname;
    private String kontakt_adresse;
    private String kontakt_plz;
    private String kontakt_ort;
    private String kontakt_telefon;
    private String kontakt_email;
    private String homepage;
    private String facebook;
    private String soundcloud;
    private String youtube;
    private String backstage;
    private List<String> kommentar;
    private String woher;
    private List<Picture> pictures;
    private List<Song> songs;
    private List<String> voters;
    private List<String> tags;

    @Override
    public String toString() {
        return "RigBand{" +
                "id=" + id +
                ", bewerbungsdatum=" + bewerbungsdatum +
                ", uid='" + uid + '\'' +
                ", status='" + status + '\'' +
                ", runde=" + runde +
                ", result=" + result +
                ", day='" + day + '\'' +
                ", name='" + name + '\'' +
                ", proberaum='" + proberaum + '\'' +
                ", beschreibung=" + beschreibung +
                ", besetzung=" + besetzung +
                ", musikstil='" + musikstil + '\'' +
                ", voice='" + voice + '\'' +
                ", gruendung='" + gruendung + '\'' +
                ", covermusik='" + covermusik + '\'' +
                ", titel=" + titel +
                ", gema=" + gema +
                ", kontakt_nachname='" + kontakt_nachname + '\'' +
                ", kontakt_vorname='" + kontakt_vorname + '\'' +
                ", kontakt_adresse='" + kontakt_adresse + '\'' +
                ", kontakt_plz='" + kontakt_plz + '\'' +
                ", kontakt_ort='" + kontakt_ort + '\'' +
                ", kontakt_telefon='" + kontakt_telefon + '\'' +
                ", kontakt_email='" + kontakt_email + '\'' +
                ", homepage='" + homepage + '\'' +
                ", facebook='" + facebook + '\'' +
                ", soundcloud='" + soundcloud + '\'' +
                ", youtube='" + youtube + '\'' +
                ", backstage='" + backstage + '\'' +
                ", kommentar=" + kommentar +
                ", woher='" + woher + '\'' +
                ", pictures=" + pictures +
                ", songs=" + songs +
                ", voters=" + voters +
                ", tags=" + tags +
                '}';
    }

    /**
     * Sets up the object according to the fields in the retrieved xml
     * document one gets when requesting a Band and its infos.
     * @param doc the document returned from the RiG server
     */
    public RigBand(Document doc) {
        super(doc);

        Element e = (Element) doc
                .getElementsByTagName("rig_band")
                .item(0);
        id = Integer.parseInt(e.getAttribute("id"));

        bewerbungsdatum =  Integer.parseInt(getContent("bewerbungsdatum"));
        uid = getContent("uid");
        status = getContent("status");
        runde = Integer.parseInt(getContent("runde"));
        result = Integer.parseInt(getContent("result"));
        day = getContent("day");
        name = getContent("name");
        proberaum = getContent("proberaum");

        beschreibung = new ArrayList<String>();
        NodeList beschreibungParas = getChildEntities("beschreibung");
        for (int i = 0; i < beschreibungParas.getLength(); i++) {
            beschreibung.add(beschreibungParas.item(i).getTextContent());
        }

        besetzung = new ArrayList<String>();
        NodeList bandMitglieder = getChildEntities("besetzung");
        for (int i = 0; i < bandMitglieder.getLength(); i++) {
            besetzung.add(bandMitglieder.item(i).getTextContent());
        }

        musikstil = getContent("musikstil");
        voice = getContent("voice");
        gruendung = getContent("gruendung");
        covermusik = getContent("covermusik");
        titel = Integer.parseInt(getContent("titel"));

        gema = getContent("gema").equals("ja") ? true : false;

        kontakt_nachname = getContent("nachname");
        kontakt_vorname = getContent("vorname");
        kontakt_adresse = getContent("adresse");
        kontakt_plz = getContent("plz");
        kontakt_ort = getContent("ort");
        kontakt_telefon = getContent("telefon");
        kontakt_email = getContent("email");

        homepage = getContent("homepage");
        facebook = getContent("facebook");
        soundcloud = getContent("soundcloud");
        youtube = getContent("youtube");
        backstage = getContent("backstage");

        kommentar = new ArrayList<String>();
        NodeList kommentarParas = getChildEntities("kommentar");
        for (int i = 0; i < kommentarParas.getLength(); i++) {
            kommentar.add(kommentarParas.item(i).getTextContent());
        }

        woher = getContent("woher");

        pictures = new ArrayList<Picture>();
        NodeList pictureEntities = getChildEntities("pictures");
        for (int i = 0; i < pictureEntities.getLength(); i++) {
            pictures.add(instPicture(pictureEntities.item(i)));
        }

        songs = new ArrayList<Song>();
        NodeList songEntities = getChildEntities("pictures");
        for (int i = 0; i < songEntities.getLength(); i++) {
            songs.add(instSong(songEntities.item(i)));
        }

        voters = new ArrayList<String>();
        NodeList voterEntities = getChildEntities("voters");
        for (int i = 0; i < voterEntities.getLength(); i++) {
            voters.add(voterEntities.item(i).getTextContent());
        }

        tags = new ArrayList<String>();
        NodeList tagEntities = getChildEntities("tags");
        for (int i = 0; i < tagEntities.getLength(); i++) {
            tags.add(tagEntities.item(i).getTextContent());
        }
    }

    private Picture instPicture(Node item) {
        Element e = (Element) item;
        Picture pic = new Picture();

        pic.setId(Integer.parseInt(e.getAttribute("id")));
        pic.setLocal(e.getAttribute("local"));
        pic.setUrl(e.getTextContent());

        return pic;
    }

    private Song instSong(Node item) {
        Element e = (Element) item;
        Song song = new Song();

        song.setId(Integer.parseInt(e.getAttribute("id")));
        song.setLocal(e.getAttribute("local"));
        song.setUrl(e.getTextContent());

        return song;
    }

    public Integer getId() {
        return id;
    }

    public Integer getBewerbungsdatum() {
        return bewerbungsdatum;
    }

    public String getUid() {
        return uid;
    }

    public String getStatus() {
        return status;
    }

    public int getRunde() {
        return runde;
    }

    public int getResult() {
        return result;
    }

    public String getDay() {
        return day;
    }

    public String getName() {
        return name;
    }

    public String getProberaum() {
        return proberaum;
    }

    public List<String> getBeschreibung() {
        return beschreibung;
    }

    public List<String> getBesetzung() {
        return besetzung;
    }

    public String getMusikstil() {
        return musikstil;
    }

    public String getVoice() {
        return voice;
    }

    public String getGruendung() {
        return gruendung;
    }

    public String getCovermusik() {
        return covermusik;
    }

    public int getTitel() {
        return titel;
    }

    public boolean isGema() {
        return gema;
    }

    public String getKontakt_nachname() {
        return kontakt_nachname;
    }

    public String getKontakt_vorname() {
        return kontakt_vorname;
    }

    public String getKontakt_adresse() {
        return kontakt_adresse;
    }

    public String getKontakt_plz() {
        return kontakt_plz;
    }

    public String getKontakt_ort() {
        return kontakt_ort;
    }

    public String getKontakt_telefon() {
        return kontakt_telefon;
    }

    public String getKontakt_email() {
        return kontakt_email;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getSoundcloud() {
        return soundcloud;
    }

    public String getYoutube() {
        return youtube;
    }

    public String getBackstage() {
        return backstage;
    }

    public List<String> getKommentar() {
        return kommentar;
    }

    public String getWoher() {
        return woher;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public List<String> getVoters() {
        return voters;
    }

    public List<String> getTags() {
        return tags;
    }
}
