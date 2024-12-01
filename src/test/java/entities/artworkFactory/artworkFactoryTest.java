package entities.artworkFactory;

import entities.Artwork;
import entities.ArtworkFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class artworkFactoryTest {

    @Test
    public void testCreateArtwork() {
        Artwork artwork = ArtworkFactory.createArtwork("Mona Lisa", "Leonardo da Vinci", "1503-1506",
                "Louvre", "image_url", "portrait, renaissance",
                "A portrait of Lisa Gherardini", "1");

        assertNotNull(artwork);
        assertEquals("Mona Lisa", artwork.getTitle());
        assertEquals("Leonardo da Vinci", artwork.getArtistName());
        assertEquals("1503-1506", artwork.getCompositionDate());
        assertEquals("Louvre", artwork.getGallery());
        assertEquals("image_url", artwork.getImageUrl());
        assertEquals("portrait, renaissance", artwork.getKeyWords());
        assertEquals("A portrait of Lisa Gherardini", artwork.getDescription());
        assertEquals("1", artwork.getId());
        assertFalse(artwork.checkFavorited());
        assertEquals(0, artwork.getRating());
        assertTrue(artwork.getComments().isEmpty());
    }
}