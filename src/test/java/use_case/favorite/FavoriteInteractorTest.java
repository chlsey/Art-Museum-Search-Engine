package use_case.favorite;

import data.InMemoryDataAccessObject;
import entities.Artwork;
import entities.ArtworkFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class FavoriteInteractorTest {

    @Test
    public void favoriteSavedArtTest() throws IOException {
        final Artwork artwork1 = ArtworkFactory.createArtwork("Starry Night", "Vincent Van Gogh", "1889",
                "MoMA", "", "starsnightbluefamous", "no description", "111");
        Artwork artwork2 = ArtworkFactory.createArtwork("Madame X", "John Singer Sargent", "1883",
                "MoMA", "", "womanblackdressportrait", "no description", "112");
        FavoriteInputData inputData = new FavoriteInputData(artwork1.getId());
        final FavoriteDataAccessInterface artworkRepository = new InMemoryDataAccessObject();

        artworkRepository.save(artwork1);
        artworkRepository.save(artwork2);

        FavoriteOutputBoundary successPresenter = new FavoriteOutputBoundary() {
            @Override
            public void getAllFavorites(FavoriteOutputData outputData) {
                // Test the output data here if needed
            }
        };

        FavoriteInputBoundary favoriteUseCase = new FavoriteInteractor(artworkRepository, successPresenter);
        favoriteUseCase.execute(inputData);
        assertTrue(artworkRepository.getArtworkById(artwork1.getId()).checkFavorited());
    }

    @Test
    public void favoriteNewArtTest() throws IOException {
        final Artwork artwork1 = ArtworkFactory.createArtwork("Starry Night", "Vincent Van Gogh", "1889",
                "MoMA", "", "starsnightbluefamous", "no description", "111");
        FavoriteInputData inputData = new FavoriteInputData(artwork1.getId());
        final FavoriteDataAccessInterface artworkRepository = new InMemoryDataAccessObject();

        FavoriteOutputBoundary successPresenter = new FavoriteOutputBoundary() {
            @Override
            public void getAllFavorites(FavoriteOutputData outputData) {
                // Test the output data here if needed
            }
        };

        FavoriteInputBoundary favoriteUseCase = new FavoriteInteractor(artworkRepository, successPresenter);
        favoriteUseCase.execute(inputData);
        assertFalse(artworkRepository.contains(artwork1.getId()));
    }

    @Test
    public void unfavoriteTest() throws IOException {
        Artwork artwork1 = ArtworkFactory.createArtwork("Starry Night", "Vincent Van Gogh", "1889",
                "MoMA", "", "starsnightbluefamous", "no description", "111");
        artwork1.setFavorited(true);
        FavoriteDataAccessInterface artworkRepository = new InMemoryDataAccessObject();
        artworkRepository.save(artwork1);
        FavoriteInputData inputData = new FavoriteInputData(artwork1.getId());

        FavoriteOutputBoundary successPresenter = new FavoriteOutputBoundary() {
            @Override
            public void getAllFavorites(FavoriteOutputData outputData) {
                // Test the output data here if needed
            }
        };
        FavoriteInputBoundary favoriteUseCase = new FavoriteInteractor(artworkRepository, successPresenter);
        favoriteUseCase.execute(inputData);
        assertFalse(artworkRepository.getArtworkById(artwork1.getId()).checkFavorited());
    }

    @Test
    public void getAllFavoritesTest() throws IOException {
        Artwork artwork1 = ArtworkFactory.createArtwork("Starry Night", "Vincent Van Gogh", "1889",
                "MoMA", "", "starsnightbluefamous", "no description", "111");
        Artwork artwork2 = ArtworkFactory.createArtwork("Madame X", "John Singer Sargent", "1883",
                "MoMA", "", "womanblackdressportrait", "no description", "112");
        artwork1.setFavorited(true);
        artwork2.setFavorited(true);
        FavoriteDataAccessInterface artworkRepository = new InMemoryDataAccessObject();
        artworkRepository.save(artwork1);
        artworkRepository.save(artwork2);

        FavoriteOutputBoundary successPresenter = new FavoriteOutputBoundary() {
            @Override
            public void getAllFavorites(FavoriteOutputData outputData) {
                List<Artwork> favorites = outputData.getFavoriteArtworks();
                assertEquals(2, favorites.size());
                assertTrue(favorites.contains(artwork1));
                assertTrue(favorites.contains(artwork2));
            }
        };

        FavoriteInputBoundary favoriteUseCase = new FavoriteInteractor(artworkRepository, successPresenter);
        favoriteUseCase.getFavoriteArtworks();
    }

    @Test
    public void getFavoritesTest() throws IOException {
        Artwork artwork1 = ArtworkFactory.createArtwork("Starry Night", "Vincent Van Gogh", "1889",
                "MoMA", "", "starsnightbluefamous", "no description", "111");
        Artwork artwork2 = ArtworkFactory.createArtwork("Madame X", "John Singer Sargent", "1883",
                "MoMA", "", "womanblackdressportrait", "no description", "112");
        artwork1.setFavorited(true);
        artwork2.setFavorited(true);
        FavoriteDataAccessInterface artworkRepository = new InMemoryDataAccessObject();
        artworkRepository.save(artwork1);
        artworkRepository.save(artwork2);

        FavoriteInputBoundary favoriteUseCase = new FavoriteInteractor(artworkRepository, null);
        List<Artwork> favorites = favoriteUseCase.getFavorites();
        assertEquals(2, favorites.size());
        assertTrue(favorites.contains(artwork1));
        assertTrue(favorites.contains(artwork2));
    }

    @Test
    public void favoriteNonExistentArtTest() throws IOException {
        final Artwork artwork1 = ArtworkFactory.createArtwork("Starry Night", "Vincent Van Gogh", "1889",
                "MoMA", "", "starsnightbluefamous", "no description", "111");
        FavoriteInputData inputData = new FavoriteInputData(artwork1.getId());
        final FavoriteDataAccessInterface artworkRepository = new InMemoryDataAccessObject();

        FavoriteOutputBoundary successPresenter = new FavoriteOutputBoundary() {
            @Override
            public void getAllFavorites(FavoriteOutputData outputData) {
                // Test the output data here if needed
            }
        };

        FavoriteInputBoundary favoriteUseCase = new FavoriteInteractor(artworkRepository, successPresenter);
        favoriteUseCase.execute(inputData);
    }
}