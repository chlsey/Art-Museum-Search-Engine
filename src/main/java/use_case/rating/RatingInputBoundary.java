package use_case.rating;

import java.io.IOException;

public interface RatingInputBoundary {
    void execute(RatingInputData ratingOutputData)  throws IOException;
}
