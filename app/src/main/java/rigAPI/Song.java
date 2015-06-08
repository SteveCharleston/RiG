package rigAPI;

/**
 * Holds all information about a Song as returned through getBand.php
 */
public class Song {
    private Integer id;
    private String local;
    private String url;

    /**
     * constructs a song without information attached to it
     */
    public Song() {
        this.id = null;
        this.local = null;
        this.url = null;
    }

    /**
     * constructs a song with the given information attached to it
     * @param id    unique id of the song
     * @param local local path to the given song
     * @param url   url to retrieve the song from
     */
    public Song(int id, String local, String url) {
        this.id = id;
        this.local = local;
        this.url = url;
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

    public int getId() {
        return id;
    }

    public String getLocal() {

        return local;
    }

    public String getUrl() {

        return url;
    }
}
