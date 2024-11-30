package use_case.rating;

import java.io.IOException;

public interface RatingOutputBoundary {
    void prepareSuccessView(RatingOutputData outputData) throws IOException;

    void prepareFailureView(String errorMessage);

}
