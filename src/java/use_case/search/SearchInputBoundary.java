package use_case.search;

import entities.Artwork;
import java.util.List;

public interface SearchInputBoundary {

    /**
     * Executes the search use case.
     * @param searchInputData the input data
     */
    List<Artwork> execute(SearchInputData searchInputData);
}
