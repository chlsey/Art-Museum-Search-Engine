package use_case.comment;

import entities.Artwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import data.InMemoryDataAccessObject;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommentInteractorTest {

    private CommentInteractor interactor;
    private Artwork artwork;
    private CommentInputData commentInputData;
    private InMemoryDataAccessObject repository;
    private CommentOutputBoundary commentOutputBoundary;

    @BeforeEach
    void setUp() {
        artwork = new Artwork("rain", "Artist", "2021", "Gallery", "image_url", "keywords", "description", "1");
        commentInputData = new CommentInputData(artwork, "cool");

        repository = new InMemoryDataAccessObject(); // Ensure this implements CommentDataAccessInterface
        repository.save(artwork); // Add artwork to the repository
        commentOutputBoundary = new CommentOutputBoundary() {
            @Override
            public void presentSuccessView(CommentOutputData outputData) {
                assertEquals("rain", outputData.getArtworkTitle());
                assertEquals("cool", outputData.getComment());
                assertTrue(outputData.isSuccess());
            }

            @Override
            public void presentFailureView(String errorMessage) {
                assertEquals("Comment cannot be empty", errorMessage);
            }
        };
        interactor = new CommentInteractor(repository, commentOutputBoundary);
    }

    @Test
    public void testExecuteSuccess() throws IOException {
        interactor.execute(commentInputData);
        // Assuming the addComment method should call presentSuccessView with correct data.
        // Implement the necessary assertions here once the method is fully implemented.
    }

    @Test
    public void testExecuteFailure() throws IOException {
        // Modify the artwork or repository to simulate a failure scenario.
        CommentInputData invalidInputData = new CommentInputData(artwork, "cool");
        interactor.execute(invalidInputData);
    }

    @Test
    public void testGetCommentedArtworks() throws IOException {
        interactor.execute(commentInputData); // Add a comment to ensure there is a commented artwork
        List<Artwork> commentedArtworks = interactor.getCommentedArtworks();
        assertNotNull(commentedArtworks);
//        assertFalse(commentedArtworks.isEmpty());
//        assertEquals("rain", commentedArtworks.get(0).getTitle());
    }

    @Test
    public void testCommentInputData() {
        assertEquals(artwork, commentInputData.getArtwork());
        assertEquals("cool", commentInputData.getComment());
        assertEquals("rain", commentInputData.getArtworkTitle());
    }

    @Test
    public void testCommentOutputData() {
        CommentOutputData commentOutputData = new CommentOutputData("rain", "cool", true);
        assertEquals("rain", commentOutputData.getArtworkTitle());
        assertEquals("cool", commentOutputData.getComment());
        assertTrue(commentOutputData.isSuccess());
    }
}