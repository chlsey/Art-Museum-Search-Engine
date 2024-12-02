package use_case.favorite;

/**
 * Favorite input data.
 */
public class FavoriteInputData {
    private final String id;

    /**
     * Favorite input data.
     * @param id id
     */
    public FavoriteInputData(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
