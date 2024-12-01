package entities.artwork;

import entities.Artwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class artworkTest {
    private Artwork artwork;

    @BeforeEach
    public void setUp() {
        artwork = new Artwork("Mona Lisa", "Leonardo da Vinci", "1503-1506", "Louvre",
                "image_url", "portrait, renaissance", "A portrait of Lisa Gherardini", "1");
    }

    @Test
    public void testGetters() {
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

    @Test
    public void testSetters() {
        artwork.setTitle("Starry Night");
        assertEquals("Starry Night", artwork.getTitle());

        artwork.setArtistName("Vincent van Gogh");
        assertEquals("Vincent van Gogh", artwork.getArtistName());

        artwork.setGallery("MoMA");
        assertEquals("MoMA", artwork.getGallery());

        artwork.setImageUrl("new_image_url");
        assertEquals("new_image_url", artwork.getImageUrl());

        artwork.setKeyWords("night, stars, post-impressionism");
        assertEquals("night, stars, post-impressionism", artwork.getKeyWords());

        artwork.setDescription("A depiction of a night sky");
        assertEquals("A depiction of a night sky", artwork.getDescription());

        artwork.setId("2");
        assertEquals("2", artwork.getId());

        artwork.setFavorited(true);
        assertTrue(artwork.checkFavorited());

        artwork.setRating(5);
        assertEquals(5, artwork.getRating());

        List<String> comments = new ArrayList<>();
        comments.add("Amazing!");
        artwork.setComments(comments);
        assertEquals(1, artwork.getComments().size());
        assertEquals("Amazing!", artwork.getLastComment());
    }

    @Test
    public void testAddComment() {
        artwork.addComment("Beautiful!");
        assertEquals(1, artwork.getComments().size());
        assertEquals("Beautiful!", artwork.getLastComment());
    }

    @Test
    public void testDefaultConstructor() {
        Artwork defaultArtwork = new Artwork();
        assertEquals("", defaultArtwork.getTitle());
        assertEquals("", defaultArtwork.getArtistName());
        assertEquals("", defaultArtwork.getCompositionDate());
        assertEquals("", defaultArtwork.getGallery());
        assertEquals("", defaultArtwork.getImageUrl());
        assertEquals("", defaultArtwork.getKeyWords());
        assertEquals("", defaultArtwork.getDescription());
        assertEquals("", defaultArtwork.getId());
        assertFalse(defaultArtwork.checkFavorited());
        assertEquals(0, defaultArtwork.getRating());
        assertTrue(defaultArtwork.getComments().isEmpty());
    }
}