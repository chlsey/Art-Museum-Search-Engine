package use_case.search;

import entities.Artwork;

import java.util.ArrayList;
import java.util.List;

public interface SearchDataAccessInterface {
    List<Artwork> searchArtwork(String searchMessage);
}
