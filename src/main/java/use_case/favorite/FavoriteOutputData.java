package use_case.favorite;

import entities.Artwork;

import java.util.ArrayList;
import java.util.List;

public class FavoriteOutputData {
    //private boolean favorited;
    private final List<Artwork> favoritedArtworks;

    public FavoriteOutputData(List<Artwork> favoritedArtworks) {
        //favorited = artwork.checkFavorited();
        this.favoritedArtworks = favoritedArtworks;
    }

//    public boolean getFavoritedArtworks() {
//        return favorited;
//    }

    public List<Artwork> getFavoriteArtworks() {return favoritedArtworks;}
}
