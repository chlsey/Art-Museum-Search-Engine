package use_case.click_art;
import entities.Artwork;
import org.junit.Test;
import data.InMemoryDataAccessObject;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;

public class clickartInteractorTest {

    @BeforeEach
    void beforeEach() {

    }
    @Test
    public void clickArtInteractorTest() {
        Artwork artwork = new Artwork("sky", "Charles Dewolf Brownell", "", "", "", "", "The American Wing, Oil on academy board, , Painting ");
        ClickArtInputData clickArtInputData = new ClickArtInputData(artwork);
        ClickArtDataAccessInterface respository = new InMemoryDataAccessObject();
        ClickArtOutputBoundary clickArtOutputBoundary = new ClickArtOutputBoundary() {
            @Override
            public void prepareSuccessView(ClickArtOutputData outputData) {
                assertEquals("sky", artwork.getTitle());
            }

            @Override
            public void prepareFailView(String errorMessage) {

            }
        };
    }
}
