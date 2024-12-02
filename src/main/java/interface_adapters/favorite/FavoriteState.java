package interface_adapters.favorite;

/**
 * Favorite State class.
 */
public class FavoriteState {
    private Boolean favorite = false;

    public FavoriteState() {
    }

    /**
     * Set favorite.
     */
    public void setFavorite() {
        this.favorite = !this.favorite;
    }

    public Boolean getFavorite() {
        return this.favorite;
    }
}
