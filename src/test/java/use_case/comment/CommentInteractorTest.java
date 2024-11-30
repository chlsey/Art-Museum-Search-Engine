package use_case.comment;
import org.junit.Test;
import data.InMemoryDataAccessObject;
import entities.Artwork;
import use_case.comment.CommentDataAccessInterface;
import use_case.comment.CommentInputData;
import use_case.comment.CommentOutputBoundary;
import use_case.comment.CommentOutputData;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;


public class CommentInteractorTest {
    @BeforeEach
    void beforeEach() {

    }
    @Test
    public void commentInteractorTest() {
        Artwork artwork = new Artwork("rain", "", "", "", "", "", "", "");
        CommentInputData commentInputData = new CommentInputData(artwork, "cool");
        CommentDataAccessInterface repository = new InMemoryDataAccessObject();
        CommentOutputBoundary commentOutputBoundary = new CommentOutputBoundary() {
            @Override
            public void presentSuccessView(CommentOutputData outputData) {
                assertEquals("rain", outputData.getComment());
            }

            @Override
            public void presentFailureView(String errorMessage) {

            }

        };
    }
    @Test
    public void commentInteractorTestAgain() {
        Artwork artwork = new Artwork("Head of a ruler", "", "", "", "", "", "", "");
        CommentInputData commentInputData = new CommentInputData(artwork, "brother eww");
        CommentDataAccessInterface repository = new InMemoryDataAccessObject();
        CommentOutputBoundary commentOutputBoundary = new CommentOutputBoundary() {

            @Override
            public void presentSuccessView(CommentOutputData outputData) {
                assertEquals("Head of a ruler", outputData.getComment());
            }

            @Override
            public void presentFailureView(String errorMessage) {

            }

        };

    };
}
