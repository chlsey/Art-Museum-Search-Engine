package interface_adapters.search;

import java.util.List;

import entities.Artwork;

/**
 * The state for the Search View Model.
 */
public class SearchState {
    private String keywords;
    private String timePeriod = "";
    private String location = "";

    private String keywordsError;
    private String timePeriodError;
    private String locationError;
    private boolean viewingDetails = false;
    private Artwork selectedArtwork;
    private List<Artwork> artworks;

    public String getKeywords() {
        return keywords;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public String getLocation() {
        return location;
    }

    public String getKeywordsError() {
        return keywordsError;
    }

    public String getTimePeriodError() {
        return timePeriodError;
    }

    public String getLocationError() {
        return locationError;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setKeywordsError(String keywordsError) {
        this.keywordsError = keywordsError;
    }

    public void setTimePeriodError(String timePeriodError) {
        this.timePeriodError = timePeriodError;
    }

    public void setLocationError(String locationError) {
        this.locationError = locationError;
    }

    @Override
    public String toString() {
        return "SearchState{"
                + "keywords='" + keywords + '\''
                + ", timePeriod='" + timePeriod + '\''
                + ", location='" + location + '\''
                + '}';
    }

    public boolean isViewingDetails() {
        return viewingDetails;
    }

    public Artwork getSelectedArtwork() {
        return selectedArtwork;
    }

    public void setSelectedArtwork(Artwork selectedArtwork) {
        this.selectedArtwork = selectedArtwork;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }
}

