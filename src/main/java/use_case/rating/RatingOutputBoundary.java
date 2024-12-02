package use_case.rating;

import java.io.IOException;

/**
 * Rating Outpput Boundary.
 */
public interface RatingOutputBoundary {
    /**
     * Prepare success view.
     * @param outputData outputData
     * @throws IOException exception
     */
    void prepareSuccessView(RatingOutputData outputData) throws IOException;

    /**
     * Prepare failure view.
     * @param errorMessage errorMessage
     */
    void prepareFailureView(String errorMessage);

}
