package use_case.comment;
import entities.*;
import org.junit.Before;
import org.junit.Test;
import data.InMemoryDataAccessObject;
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
