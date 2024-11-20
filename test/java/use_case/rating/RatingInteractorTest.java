package use_case.rating;
import entities.*;
import org.junit.Before;
import org.junit.Test;
import data.InMemoryDataAccessObject;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;
public class RatingInteractorTest {



    @BeforeEach
    void beforeEach() {

    }
    @Test
    public void ratingTest() {
        RatingInputData ratingInputData = new RatingInputData(5);
        RatingDataAccessInterface repository = (RatingDataAccessInterface) new InMemoryDataAccessObject();
        RatingOutputBoundary ratingOutputBoundary = new RatingOutputBoundary() {
            @Override
            public void prepareRatingView(RatingOutputData data) {
                assertEquals(5, repository.getRating());
            }
        };

    }

    @Test
    public void ratingTestAgain() {
        RatingInputData ratingInputData = new RatingInputData(6);
        RatingDataAccessInterface repository = (RatingDataAccessInterface) new InMemoryDataAccessObject();
        RatingOutputBoundary ratingOutputBoundary = new RatingOutputBoundary() {
            @Override
            public void prepareRatingView(RatingOutputData data) {
                assertEquals(6, repository.getRating());
            }
        };

    }
}












//public class RatingInteractorTest {
//
//    private RatingInteractor interactor;
//    private RatingDataAccessInterface ratingDataAccessObject;
//    private MockRatingOutputBoundary mockOutputBoundary;
//
//    @Before
//    public void setUp() {
//        ratingDataAccessObject = (RatingDataAccessInterface) new InMemoryDataAccessObject();
//        mockOutputBoundary = new MockRatingOutputBoundary();
//        interactor = new RatingInteractor(ratingDataAccessObject, mockOutputBoundary);
//    }
//
//    @Test
//    public void testRatingInteractor() {
//        RatingOutputData inputData = new RatingOutputData(4, 0.0); // Initialize with a rating value
//
//        // Execute the interactor with the input data
//        interactor.execute(inputData);
//
//        // Verify the results
//        assertEquals(4, mockOutputBoundary.getOutputData().getRating());
//        double expectedAverageRating = 4.0; // Assuming it's the only rating so far
//        assertEquals(expectedAverageRating, mockOutputBoundary.getOutputData().getAverageRating(), 0.01);
//    }
//
//    // Mock implementation of RatingOutputBoundary
//    private class MockRatingOutputBoundary implements RatingOutputBoundary {
//        private RatingOutputData outputData;
//
//        @Override
//        public void prepareRatingView(RatingOutputData data) {
//            this.outputData = data;
//        }
//
//        public RatingOutputData getOutputData() {
//            return outputData;
//        }
//    }
//}
