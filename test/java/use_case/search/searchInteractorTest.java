package use_case.search;

import data.InMemoryDataAccessObject;
import entities.Artwork;
import entities.ArtworkFactory;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

public class searchInteractorTest {
    @Test
    public void searchArtTest() throws IOException {
        final Artwork artwork1 = ArtworkFactory.createArtwork("Starry Night", "Vincent Van Gogh", "1889",
                "MoMA", "", "starsnightbluefamous", "no description", "111");
        Artwork artwork2 = ArtworkFactory.createArtwork("Madame X", "John Singer Sargent", "1883",
                "MoMA", "", "womanblackdressportrait", "no description", "112");
        final InMemoryDataAccessObject dao = new InMemoryDataAccessObject();
        dao.save(artwork1);
        dao.save(artwork2);
//        dao.searchArtwork("star");

        SearchInputData inputData = new SearchInputData("Starry Night");

        SearchDataAccessInterface artworkRepository = new InMemoryDataAccessObject();

        SearchOutputBoundary successPresenter = new SearchOutputBoundary() {

            @Override
            public void prepareSuccessView(SearchOutputData outputData) {
                assertEquals( artwork1.getTitle(), dao.searchArtwork("Starry Night").get(0).getTitle() );
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("can't fail to search!");
            }


        };

        SearchInputBoundary ratingUC = new SearchInteractor(artworkRepository, successPresenter);
        ratingUC.execute(inputData);
    }



}














