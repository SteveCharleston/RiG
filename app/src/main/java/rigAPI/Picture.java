package rigAPI;

/**
 * Created by steven on 06.05.15.
 */

/**
 * Holds all information about a single picture as returned through getBand.php
 */
public class Picture {
    private Integer id;
    private String local;
    private String url;

    /**
     * contructs a picture without information attached to it
     */
    public Picture() {
        this.id = null;
        this.local = null;
        this.url = null;
    }

    /**
     * constructs a picture with the given information attached to it
     * @param id    unique id of the picture
     * @param local local path of the given picture
     * @param url   url to retrieve the picture from
     */
    public Picture(int id, String local, String url) {
        this.id = id;
        this.local = local;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getLocal() {

        return local;
    }

    public String getUrl() {

        return url;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLocal(String local) {

        this.local = local;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
