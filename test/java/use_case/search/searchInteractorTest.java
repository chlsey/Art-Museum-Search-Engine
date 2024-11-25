package use_case.search;
import entities.*;
import org.junit.Before;
import org.junit.Test;
import data.InMemoryDataAccessObject;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;
public class searchInteractorTest {
    @BeforeEach
    void beforeEach() {

    }
    @Test
    public void searchTest() {
        SearchInputData searchInputData = new SearchInputData("knife");
        SearchDataAccessInterface repository = new InMemoryDataAccessObject();
        SearchOutputBoundary searchOutputBoundary = new SearchOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchOutputData outputData) {
                //test???

            }

            @Override
            public void prepareFailView(String errorMessage) {

            }
        };
    }
}
