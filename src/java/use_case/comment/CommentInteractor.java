package use_case.comment;
import interface_adapters.search.SearchViewModel;

public class CommentInteractor implements CommentInputBoundary {
    private final CommentDataAccessInterface commentDataAccessInterface;
    private final CommentOutputBoundary commentOutputBoundary;



    public CommentInteractor(CommentDataAccessInterface commentDataAccessInterface, CommentOutputBoundary commentOutputBoundary){
        this.commentDataAccessInterface = commentDataAccessInterface;
        this.commentOutputBoundary = commentOutputBoundary;
    }

    @Override
    public void execute(CommentInputBoundary commentInputBoundary) {

    }

    @Override
    public void execute(CommentInputData commentInputData) {
        if () {

        }

    }

}
