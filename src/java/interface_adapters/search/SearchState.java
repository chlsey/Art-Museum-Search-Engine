package java.interface_adapters.search;

/**
 * The state for the Search View Model.
 */
public class SearchState {
    private String keywords = "";
    private String genre = "";
    private String timePeriod = "";
    private String location = "";

    private String keywordsError;
    private String genreError;
    private String timePeriodError;
    private String locationError;

    public String getKeywords() {
        return keywords;
    }

    public String getGenre() {
        return genre;
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

    public String getGenreError() {
        return genreError;
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

    public void setGenre(String genre) {
        this.genre = genre;
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

    public void setGenreError(String genreError) {
        this.genreError = genreError;
    }

    public void setTimePeriodError(String timePeriodError) {
        this.timePeriodError = timePeriodError;
    }

    public void setLocationError(String locationError) {
        this.locationError = locationError;
    }

    @Override
    public String toString() {
        return "SearchState{" +
                "keywords='" + keywords + '\'' +
                ", genre='" + genre + '\'' +
                ", timePeriod='" + timePeriod + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}

