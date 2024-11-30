package use_case.click_art;

import entities.Artwork;

public class ClickArtOutputData {
//    private String artUrl;
//    private String title;
//    private String description;
//    private String artistName;
//    private String timePeriod;
    private Artwork artwork;


    public ClickArtOutputData(Artwork artwork) {
//        this.artUrl = artUrl;
//        this.title = title;
//        this.artistName = artistName;
//        this.description = description;
//        this.timePeriod = timePeriod;
        this.artwork = artwork;

    }
    public String getArtUrl() {return artwork.getImageUrl();}
    public String getTitle() {return this.artwork.getTitle();}
    public String getDescription() {return this.artwork.getDescription();}
    public String getArtistName() {return this.artwork.getArtistName();}
    public String getTimePeriod() {return this.artwork.getTimePeriod();}


}
