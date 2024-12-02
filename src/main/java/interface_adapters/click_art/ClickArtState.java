package interface_adapters.click_art;

import java.util.List;

import entities.Artwork;

/**
 * Click art state class.
 */
public class ClickArtState {
    private String clickArtError;
    private String artUrl;
    private String title;
    private String description;
    private String artistName;
    private String timePeriod;
    private List<Artwork> artworks;

    /**
     * Click art state.
     * @param clickArtError clickArtError
     * @param artUrl artUrl
     * @param title title
     * @param description description
     * @param artistName artistName
     * @param timePeriod timePeriod
     */
    public ClickArtState(String clickArtError, String artUrl, String title, String description,
                         String artistName, String timePeriod) {
        this.clickArtError = clickArtError;
        this.artUrl = artUrl;
        this.title = title;
        this.description = description;
        this.artistName = artistName;
        this.timePeriod = timePeriod;

    }

    public ClickArtState() {

    }

    public String getClickArtError() {
        return clickArtError;
    }

    public void setClickArtError(String clickArtError) {
        this.clickArtError = clickArtError;
    }

    public String getArtUrl() {
        return artUrl;
    }

    public void setArtUrl(String artUrl) {
        this.artUrl = artUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return null;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }
}
