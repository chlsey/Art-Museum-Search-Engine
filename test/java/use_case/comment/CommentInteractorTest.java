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
        CommentInputData commentInputData = new CommentInputData("rain", "cool");
    }
}
