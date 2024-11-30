package interface_adapters.favorite;

public class FavoriteState {
    private Boolean favorite = false;


    public FavoriteState() {}

    public void setFavorite() { this.favorite = !this.favorite; }

    public Boolean getFavorite() { return this.favorite; }

}
