package use_case.click_art;

public class ClickArtOutputData {
    private String artUrl;
    private String title;
    private String description;
    private String artistName;
    private String timePeriod;


    public ClickArtOutputData(String artUrl, String title, String description,
                              String artistName, String timePeriod) {
        this.artUrl = artUrl;
        this.title = title;
        this.artistName = artistName;
        this.description = description;
        this.timePeriod = timePeriod;

    }
    public String getArtUrl() {return artUrl;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public String getArtistName() {return artistName;}
    public String getTimePeriod() {return timePeriod;}


}
