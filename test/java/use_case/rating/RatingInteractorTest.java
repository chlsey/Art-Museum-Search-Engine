package use_case.rating;

import data.InMemoryDataAccessObject;
import entities.Artwork;
import entities.ArtworkFactory;
import org.junit.Test;
import use_case.favorite.*;

import java.io.IOException;

import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

public class RatingInteractorTest {
    @Test
    public void ratingSavedArtTest() throws IOException {
        Artwork artwork1 = ArtworkFactory.createArtwork("Starry Night", "Vincent Van Gogh", "1889",
                "MoMA", "", "starsnightbluefamous", "no description", "111");
        Artwork artwork2 = ArtworkFactory.createArtwork("Madame X", "John Singer Sargent", "1883",
                "MoMA", "", "womanblackdressportrait", "no description", "112");
        artwork2.setRating(2);
        artwork1.setRating(3);
        RatingInputData inputData = new RatingInputData(artwork2.getId(),artwork2.getRating());

        RatingDataAccessInterface artworkRepository = new InMemoryDataAccessObject();


        artworkRepository.save(artwork1);
        artworkRepository.save(artwork2);

        RatingOutputBoundary successPresenter = new RatingOutputBoundary() {
            @Override
            public void prepareSuccessView(RatingOutputData ratingOutputData) throws IOException {
                assertEquals(artworkRepository.getArtworkById(artwork1.getId()).getRating(), artwork1.getRating());

            }

            @Override
            public void prepareFailureView(String errorMessage) {
                fail("Can't fail to rate!");
            }
        };


        RatingInputBoundary ratingUC = new RatingInteractor(artworkRepository, successPresenter);
        ratingUC.execute(inputData);
    }

    @Test
    public void ratingForNewArt() throws IOException {
        Artwork artwork = ArtworkFactory.createArtwork("Starry Night", "Vincent Van Gogh", "1889",
                "MoMA", "", "starsnightbluefamous", "no description", "111");
        RatingInputData inputData = new RatingInputData(artwork.getId(),artwork.getRating());
        RatingDataAccessInterface artworkRepository = new InMemoryDataAccessObject();
        artworkRepository.save(artwork);

        artwork.setRating(5);
        RatingOutputBoundary successPresenter = new RatingOutputBoundary() {
            @Override
            public void prepareSuccessView(RatingOutputData outputData) throws IOException {
                assertEquals(artworkRepository.getArtworkById(artwork.getId()).getRating(), artwork.getRating());
            }

            @Override
            public void prepareFailureView(String errorMessage) {
                fail("Can't fail to rate!");
            }
        };

        RatingInputBoundary rating = new RatingInteractor(artworkRepository, successPresenter);
        rating.execute(inputData);
    }

}














