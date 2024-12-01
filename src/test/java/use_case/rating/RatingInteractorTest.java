package use_case.rating;

import data.InMemoryDataAccessObject;
import entities.Artwork;
import entities.ArtworkFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class RatingInteractorTest {

    @Test
    public void ratingSavedArtTest() throws IOException {
        final Artwork artwork1 = ArtworkFactory.createArtwork("Starry Night", "Vincent Van Gogh", "1889",
                "MoMA", "", "starsnightbluefamous", "no description", "111");
        Artwork artwork2 = ArtworkFactory.createArtwork("Madame X", "John Singer Sargent", "1883",
                "MoMA", "", "womanblackdressportrait", "no description", "112");
        artwork2.setRating(2);
        artwork1.setRating(3);
        RatingInputData inputData = new RatingInputData(artwork2.getId(), artwork2.getRating());

        final RatingDataAccessInterface artworkRepository = new InMemoryDataAccessObject();

        artworkRepository.save(artwork1);
        artworkRepository.save(artwork2);

        RatingOutputBoundary successPresenter = new RatingOutputBoundary() {
            @Override
            public void prepareSuccessView(RatingOutputData ratingOutputData) throws IOException {
                assertEquals(artworkRepository.getArtworkById(artwork1.getId()).getRating(), artwork1.getRating());
                assertEquals(artwork2.getRating(), ratingOutputData.getRating());
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
        final Artwork artwork = ArtworkFactory.createArtwork("Starry Night", "Vincent Van Gogh", "1889",
                "MoMA", "", "starsnightbluefamous", "no description", "111");
        RatingInputData inputData = new RatingInputData(artwork.getId(), artwork.getRating());
        final RatingDataAccessInterface artworkRepository = new InMemoryDataAccessObject();
        artworkRepository.save(artwork);

        artwork.setRating(5);
        RatingOutputBoundary successPresenter = new RatingOutputBoundary() {
            @Override
            public void prepareSuccessView(RatingOutputData outputData) throws IOException {
                assertEquals(artworkRepository.getArtworkById(artwork.getId()).getRating(), artwork.getRating());
                assertEquals(artwork.getRating(), outputData.getRating());
            }

            @Override
            public void prepareFailureView(String errorMessage) {
                fail("Can't fail to rate!");
            }
        };

        RatingInputBoundary rating = new RatingInteractor(artworkRepository, successPresenter);
        rating.execute(inputData);
    }

    @Test
    public void testRatingInputData() {
        RatingInputData inputData = new RatingInputData("111", 5);
        assertEquals("111", inputData.getArtworkId());
        assertEquals(5, inputData.getRating());
        assertEquals("111", inputData.getId()); // Test for getId method
    }

    @Test
    public void testRatingOutputData() {
        RatingOutputData outputData = new RatingOutputData(5);
        assertEquals(5, outputData.getRating());
    }

    @Test
    public void testGetRatedArtworks() throws IOException {
        final Artwork artwork1 = ArtworkFactory.createArtwork("Starry Night", "Vincent Van Gogh", "1889",
                "MoMA", "", "starsnightbluefamous", "no description", "111");
        final Artwork artwork2 = ArtworkFactory.createArtwork("Madame X", "John Singer Sargent", "1883",
                "MoMA", "", "womanblackdressportrait", "no description", "112");
        artwork1.setRating(5);
        artwork2.setRating(4);

        final RatingDataAccessInterface artworkRepository = new InMemoryDataAccessObject();
        artworkRepository.save(artwork1);
        artworkRepository.save(artwork2);

        RatingOutputBoundary successPresenter = new RatingOutputBoundary() {
            @Override
            public void prepareSuccessView(RatingOutputData ratingOutputData) throws IOException {
                // No implementation needed for this test
            }

            @Override
            public void prepareFailureView(String errorMessage) {
                // No implementation needed for this test
            }
        };

        RatingInputBoundary ratingUC = new RatingInteractor(artworkRepository, successPresenter);
        List<Artwork> ratedArtworks = ratingUC.getRatedArtworks();
        System.out.println("Rated artworks size: " + ratedArtworks.size()); // Debug statement
        assertEquals(2, ratedArtworks.size());
        assertTrue(ratedArtworks.contains(artwork1));
        assertTrue(ratedArtworks.contains(artwork2));
    }

    @Test
    public void ratingNonExistentArtTest() throws IOException {
        final Artwork artwork = ArtworkFactory.createArtwork("Starry Night", "Vincent Van Gogh", "1889",
                "MoMA", "", "starsnightbluefamous", "no description", "111");
        RatingInputData inputData = new RatingInputData(artwork.getId(), 5);
        final RatingDataAccessInterface artworkRepository = new InMemoryDataAccessObject();

        RatingOutputBoundary successPresenter = new RatingOutputBoundary() {
            @Override
            public void prepareSuccessView(RatingOutputData outputData) throws IOException {
                fail("Should not succeed for non-existent artwork!");
            }

            @Override
            public void prepareFailureView(String errorMessage) {
                assertEquals("Artwork does not exist.", errorMessage);
            }
        };

        RatingInputBoundary rating = new RatingInteractor(artworkRepository, successPresenter);
        rating.execute(inputData);
    }
}