package interface_adapters.click_art;

public class ClickArtState {
    private String clickArtError;
    private String imagePath;
    private String imageName;

    public ClickArtState(String clickArtError, String imagePath, String imageName) {
        this.clickArtError = clickArtError;
        this.imagePath = imagePath;
        this.imageName = imageName;
    }

    public ClickArtState() {

    }

    public String getClickArtError() {
        return clickArtError;
    }
    public void setClickArtError(String clickArtError) {
        this.clickArtError = clickArtError;
    }
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getImageName() {
        return imageName;
    }
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
