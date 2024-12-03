package use_case.filter;

import data.InMemoryDataAccessObject;
import entities.Artwork;
import entities.ArtworkFactory;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class filterInteractorTest {

    @Test
    public void filterTest() throws IOException {
        Artwork artwork1 = ArtworkFactory.createArtwork("Starry Night", "Vincent Van Gogh", "1889",
                "MoMA", "", "starsnightbluefamous", "no description", "111");
        Artwork artwork2 = ArtworkFactory.createArtwork("Madame X", "John Singer Sargent", "1883",
                "MoMA", "", "womanblackdressportrait", "no description", "112");
        final String[] filter = new String[1];
        InMemoryDataAccessObject dao = new InMemoryDataAccessObject();
        dao.save(artwork1);
        dao.save(artwork2);

        FilterInputData inputData = new FilterInputData("Artist");

        FilterDataAccessInterface artworkRepository = new InMemoryDataAccessObject();

        FilterOutputBoundary successPresenter = new FilterOutputBoundary() {
            @Override
            public void setFilter(FilterOutputData filterOutputData) {
                filter[0] = filterOutputData.getFilter();
            }
        };

        FilterInputBoundary filterUseCase = new FilterInteractor(artworkRepository, successPresenter);
        filterUseCase.execute(inputData);
    }
}