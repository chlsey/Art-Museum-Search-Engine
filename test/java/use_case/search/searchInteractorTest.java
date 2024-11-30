package use_case.search;

import entities.*;
import org.junit.Before;
import org.junit.Test;
import data.InMemoryDataAccessObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class searchInteractorTest {

    private SearchInputData searchInputData;
    private SearchDataAccessInterface repository;
    private SearchOutputBoundary searchOutputBoundary;

    @BeforeEach
    void beforeEach() {
        searchInputData = new SearchInputData("knife");
        repository = new InMemoryDataAccessObject();
        searchOutputBoundary = new SearchOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchOutputData outputData) {
                // You can add assertions to check the output data here.
            }

            @Override
            public void prepareFailView(String errorMessage) {
                // You can add assertions to check the error message here.
            }
        };
    }

//    @Test
//    public void searchTest() {
//        SearchInteractor interactor = new SearchInteractor(repository, searchOutputBoundary);
//        interactor.search(searchInputData);
//
//        // Example assertion (You might want to add actual meaningful assertions here)
//        assertEquals("Expected search result", repository.getResult());
//    }
//
//    @Test
//    public void testPrepareSuccessView() {
//        assertThrows(Exception.class, () -> {
//            searchOutputBoundary.prepareSuccessView(null);
//        });
//    }
}
