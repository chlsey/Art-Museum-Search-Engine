package use_case.favorite;

import data.InMemoryDataAccessObject;
import entities.Artwork;
import entities.ArtworkFactory;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static org.junit.Assert.*;

public class FavoriteInteractorTest {

    @Test
    public void favoriteSavedArtTest() throws IOException {
        final Artwork artwork1 = ArtworkFactory.createArtwork("Starry Night", "Vincent Van Gogh", "1889",
                "MoMA", "", "starsnightbluefamous", "no description", "111");
        Artwork artwork2 = ArtworkFactory.createArtwork("Madame X", "John Singer Sargent", "1883",
                "MoMA", "", "womanblackdressportrait", "no description", "112");
        FavoriteInputData inputData = new FavoriteInputData(artwork1);
        final FavoriteDataAccessInterface artworkRepository = new InMemoryDataAccessObject();

        artworkRepository.save(artwork1);
        artworkRepository.save(artwork2);

        FavoriteOutputBoundary successPresenter = new FavoriteOutputBoundary() {
            @Override
            public void prepareSuccessView(FavoriteOutputData outputData) throws IOException {
                assertTrue(artworkRepository.getArtworkById(artwork1.getId()).checkFavorited());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        FavoriteInputBoundary favoriteUseCase = new FavoriteInteractor(artworkRepository, successPresenter);
        favoriteUseCase.execute(inputData);
    }

    @Test
    public void favoriteNewArtTest() throws IOException {
        final Artwork artwork1 = ArtworkFactory.createArtwork("Starry Night", "Vincent Van Gogh", "1889",
                "MoMA", "", "starsnightbluefamous", "no description", "111");
        FavoriteInputData inputData = new FavoriteInputData(artwork1);
        final FavoriteDataAccessInterface artworkRepository = new InMemoryDataAccessObject();

        FavoriteOutputBoundary successPresenter = new FavoriteOutputBoundary() {
            @Override
            public void prepareSuccessView(FavoriteOutputData outputData) throws IOException {
                assertTrue(artworkRepository.getArtworkById(artwork1.getId()).checkFavorited());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        FavoriteInputBoundary favoriteUseCase = new FavoriteInteractor(artworkRepository, successPresenter);
        favoriteUseCase.execute(inputData);
    }

    @Test
    public void unfavoriteTest() throws IOException {
        Artwork artwork1 = ArtworkFactory.createArtwork("Starry Night", "Vincent Van Gogh", "1889",
                "MoMA", "", "starsnightbluefamous", "no description", "111");
        artwork1.setFavorited();
        FavoriteInputData inputData = new FavoriteInputData(artwork1);
        FavoriteDataAccessInterface artworkRepository = new InMemoryDataAccessObject();

        FavoriteOutputBoundary successPresenter = new FavoriteOutputBoundary() {
            @Override
            public void prepareSuccessView(FavoriteOutputData outputData) throws IOException {
                assertFalse(artworkRepository.getArtworkById(artwork1.getId()).checkFavorited());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        FavoriteInputBoundary favoriteUseCase = new FavoriteInteractor(artworkRepository, successPresenter);
        favoriteUseCase.execute(inputData);
    }
}
