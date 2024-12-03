package use_case.click_art;

import entities.Artwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import data.MockMuseumDataAccessObject;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class clickArtInteractorTest {

    private ClickArtInteractor interactor;
    private Artwork artwork;
    private ClickArtInputData clickArtInputData;
    private MockMuseumDataAccessObject repository;
    private ClickArtOutputBoundary clickArtOutputBoundary;

    @BeforeEach
    void setUp() {
        artwork = new Artwork("sky", "Charles Dewolf Brownell", "1850", "Metropolitan Museum of Art",
                "image_url", "landscape, oil painting", "A beautiful landscape painting", "1");
        clickArtInputData = new ClickArtInputData(artwork);
        repository = new MockMuseumDataAccessObject(); // Use the mock implementation
        clickArtOutputBoundary = new ClickArtOutputBoundary() {
            @Override
            public void prepareSuccessView(ClickArtOutputData outputData) {
                assertEquals("sky", outputData.getTitle());
                assertEquals("Charles Dewolf Brownell", outputData.getArtistName());
                assertEquals("1850", outputData.getTimePeriod());
                assertEquals("A beautiful landscape painting", outputData.getDescription());
                assertEquals("image_url", outputData.getArtUrl());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("can't fail to click!");
            }

            @Override
            public void switchToSearchView() {
                // Test switch to search view
            }

            @Override
            public void switchToCFRView(ClickArtOutputData clickArtOutputData) {
                assertEquals("sky", clickArtOutputData.getTitle());
            }
        };
        interactor = new ClickArtInteractor(repository, clickArtOutputBoundary);
    }

    @Test
    public void testExecuteSuccess() throws IOException {
        interactor.execute(clickArtInputData);
        // Assuming the execute method should call prepareSuccessView with correct data.
        // Implement the necessary assertions here once the method is fully implemented.
    }

    @Test
    public void testExecuteFailure() {
        // Modify the artwork or repository to simulate a failure scenario.
        // Implement the necessary assertions here once the method is fully implemented.
    }

    @Test
    public void testSwitchToSearchView() {
        interactor.switchToSearchView();
        // Verify that switchToSearchView was called on the output boundary.
        // Implement the necessary assertions here.
    }

    @Test
    public void testSwitchToCFRView() {
        interactor.switchToCFRView(clickArtInputData);
        // Verify that switchToCFRView was called on the output boundary with correct data.
        // Implement the necessary assertions here.
    }

    @Test
    public void testClickArtInputData() {
        assertEquals(artwork, clickArtInputData.getArtwork());
    }

    @Test
    public void testClickArtOutputData() {
        ClickArtOutputData clickArtOutputData = new ClickArtOutputData(artwork);
        assertEquals("image_url", clickArtOutputData.getArtUrl());
        assertEquals("sky", clickArtOutputData.getTitle());
        assertEquals("A beautiful landscape painting", clickArtOutputData.getDescription());
        assertEquals("Charles Dewolf Brownell", clickArtOutputData.getArtistName());
        assertEquals("1850", clickArtOutputData.getTimePeriod());
    }
}