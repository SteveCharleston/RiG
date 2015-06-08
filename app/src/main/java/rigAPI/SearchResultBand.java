package rigAPI;

/**
 * Contains the values of bands returned as search by searchBand.php
 */
public class SearchResultBand {
    int id;
    String name;

    public SearchResultBand(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "SearchResultBand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
