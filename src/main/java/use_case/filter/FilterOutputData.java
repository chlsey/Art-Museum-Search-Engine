package use_case.filter;

public class FilterOutputData {
    private final String filter;
    private final Boolean usecaseFailed;

    public FilterOutputData(String filter, Boolean usecaseFailed) {
        this.filter = filter;
        this.usecaseFailed = usecaseFailed;
    }

    public String getFilter() {
        return filter;
    }

}
