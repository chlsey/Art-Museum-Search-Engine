package use_case.search;

import data.InMemoryDataAccessObject;
import entities.Artwork;
import entities.ArtworkFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class searchInteractorTest {

    @Test
    public void searchArtTest() throws IOException {
        final Artwork artwork1 = ArtworkFactory.createArtwork("Starry Night", "Vincent Van Gogh", "1889",
                "MoMA", "", "starsnightbluefamous", "no description", "111");
        Artwork artwork2 = ArtworkFactory.createArtwork("Madame X", "John Singer Sargent", "1883",
                "MoMA", "", "womanblackdressportrait", "no description", "112");
        Artwork artwork3 = null;
        final InMemoryDataAccessObject dao = new InMemoryDataAccessObject();
        dao.save(artwork1);
        dao.save(artwork2);

        SearchInputData inputData = new SearchInputData("Starry Night");

        SearchDataAccessInterface artworkRepository = new InMemoryDataAccessObject();
        ((InMemoryDataAccessObject) artworkRepository).save(artwork1);
        ((InMemoryDataAccessObject) artworkRepository).save(artwork2);

        SearchOutputBoundary successPresenter = new SearchOutputBoundary() {

            @Override
            public void prepareSuccessView(SearchOutputData outputData) {
                System.out.println("Search results size: " + outputData.getArtworks().size()); // Debug statement
                assertEquals(artwork1.getTitle(), outputData.getArtworks().get(0).getTitle());
                assertFalse(outputData.isUseCaseFailed());
            }

            @Override
            public void prepareFailView(String errorMessage) {

                fail("can't fail to search!");
            }
        };

        SearchInputBoundary searchUC = new SearchInteractor(artworkRepository, successPresenter);
        searchUC.execute(inputData);
    }
}