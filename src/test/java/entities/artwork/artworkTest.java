package entities.artwork;

import entities.Artwork;
import entities.ArtworkFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class artworkTest {
    private Artwork artwork1;
    private Artwork artwork2;

    @BeforeEach
    public void setUp() {
        artwork1 = new Artwork("Mona Lisa", "Leonardo da Vinci", "1503-1506", "Louvre",
                "image_url", "portrait, renaissance", "A portrait of Lisa Gherardini", "1");
        artwork2 = new Artwork("Madame X", "John Singer Sargent", "1883",
                "MoMA", "", "womanblackdressportrait", "no description", "2");
    }

    @Test
    public void testGetters() {
        assertEquals("Mona Lisa", artwork1.getTitle());
        assertEquals("Leonardo da Vinci", artwork1.getArtistName());
        assertEquals("1503-1506", artwork1.getCompositionDate());
        assertEquals("Louvre", artwork1.getGallery());
        assertEquals("image_url", artwork1.getImageUrl());
        assertEquals("portrait, renaissance", artwork1.getKeyWords());
        assertEquals("A portrait of Lisa Gherardini", artwork1.getDescription());
        assertEquals("1", artwork1.getId());
        assertFalse(artwork1.checkFavorited());
        assertEquals(0, artwork1.getRating());
        assertTrue(artwork1.getComments().isEmpty());
    }

    @Test
    public void testSetters() {
        artwork1.setTitle("Starry Night");
        assertEquals("Starry Night", artwork1.getTitle());

        artwork1.setArtistName("Vincent van Gogh");
        assertEquals("Vincent van Gogh", artwork1.getArtistName());

        artwork1.setGallery("MoMA");
        assertEquals("MoMA", artwork1.getGallery());

        artwork1.setImageUrl("new_image_url");
        assertEquals("new_image_url", artwork1.getImageUrl());

        artwork1.setKeyWords("night, stars, post-impressionism");
        assertEquals("night, stars, post-impressionism", artwork1.getKeyWords());

        artwork1.setDescription("A depiction of a night sky");
        assertEquals("A depiction of a night sky", artwork1.getDescription());

        artwork1.setId("2");
        assertEquals("2", artwork1.getId());

        artwork1.setFavorited(true);
        assertTrue(artwork1.checkFavorited());

        artwork1.setFavorited(false);
        assertFalse(artwork1.checkFavorited());

        artwork1.setRating(5);
        assertEquals(5, artwork1.getRating());

        List<String> comments = new ArrayList<>();
        comments.add("Amazing!");
        artwork1.setComments(comments);
        assertEquals(1, artwork1.getComments().size());
        assertEquals("Amazing!", artwork1.getLastComment());
    }

    @Test
    public void testAddComment() {
        artwork1.addComment("Beautiful!");
        assertEquals(1, artwork1.getComments().size());
        assertEquals("Beautiful!", artwork1.getLastComment());
    }

    @Test
    public void testAddEmptyComment() {
        assertEquals(0, artwork2.getComments().size());
        assertEquals("", artwork2.getLastComment());
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
        assertEquals("",defaultArtwork.getTimePeriod());
        assertFalse(defaultArtwork.checkFavorited());
        assertEquals(0, defaultArtwork.getRating());
        assertTrue(defaultArtwork.getComments().isEmpty());
    }
}